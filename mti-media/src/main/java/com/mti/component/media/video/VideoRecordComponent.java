package com.mti.component.media.video;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.mti.component.media.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * @anthor created by jzw
 * @date 2020/5/26
 * @change
 * @describe 视频录制组件
 **/
public class VideoRecordComponent extends AppCompatActivity implements SurfaceHolder.Callback {
    private static final String TAG = VideoRecordComponent.class.getSimpleName();
    private MaterialProgressBar mProgress;
    private SurfaceView mSurfaceview;
    private Button mBtnPlay;
    private Button mBtnRecord;
    private TextView mTvRecordTip;
    private View mLlRecordBtn;
    private TypeButton mBtnCancel;
    private TypeButton mBtnSubmit;
    private View mLlRecordOp;
    private ImageView mIvFlash;
    private ImageView mIvSwitch;
    private View mLayoutTopRight;
    //拍照模式
    public static final int TYPE_IMAGE = 1;
    //录制模式
    public static final int TYPE_VIDEO = 2;

    //录像标志
    private boolean mStartedFlag = false;
    //播放标志
    private boolean mPlayFlag = false;
    //视频录制器
    private MediaRecorder mRecorder;
    //预览的surface
    private SurfaceHolder mSurfaceHolder;
    //摄像头
    private Camera mCamera;
    //视频播放对象
    private MediaPlayer mMediaPlayer;
    //目标文件夹地址
    private String dirPath;
    //最终视频输入保存路劲
    private String videoOutputPath;
    //视频缩略图，拍照模式图片位置
    private String imgPath;
    //计时器
    private int timer = 0;
    //视频总时长,单位秒,默认15秒
    private int maxSec = 15;
    //起始时间 毫秒
    private long startTime;
    //结束时间 毫秒
    private long stopTime;
    //回收摄像头
    private boolean cameraReleaseEnable = false;
    //回收recorder 对象
    private boolean recorderReleaseEnable = false;
    //回收player 对象
    private boolean playerReleaseEnable = false;
    //默认为视频录制模式
    private int mType = TYPE_VIDEO;
    //摄像头方向，默认后置
    private int CURRENT_CAMERA_FACING = 0;
    //摄像头角度,默认后置为90度
    private int mCameraAngle = 90;
    //手机传感器倾斜角度
    private int mSensorAngle = 0;
    //传感器对象
    private SensorManager mSensorManager = null;
    //摄像头是否已经在预览状态
    private boolean mCameraPreview = false;
    //闪关灯状态
    private static final int TYPE_FLASH_AUTO = 0x021;
    private static final int TYPE_FLASH_ON = 0x022;
    private static final int TYPE_FLASH_OFF = 0x023;
    private int mTypeFlash = TYPE_FLASH_OFF;

    //权限
    private String[] permission = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };
    private static Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            timer++;
            if (timer <= 100) {
                // 之所以这里是100 是为了方便使用进度条
                mProgress.setProgress(timer);
                //之所以每一百毫秒增加一次计时器是因为：总时长的毫秒数 / 100 即每次间隔延时的毫秒数 为 100
                handler.postDelayed(this, maxSec * 10);
            } else {
                stopRecord();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.video_record);
        //最大录制时长，单位秒，默认15秒
        maxSec = getIntent().getIntExtra("maxDuration", 15);
        registerSensorManager(this);
        setUpInit();
    }


    /**
     * 初始化
     */
    private void setUpInit() {
        initViews();
        setViewClickListener();
        CURRENT_CAMERA_FACING = Camera.CameraInfo.CAMERA_FACING_BACK;
        mMediaPlayer = new MediaPlayer();
        SurfaceHolder holder = mSurfaceview.getHolder();
        holder.addCallback(this);
        mSurfaceview.setVisibility(View.VISIBLE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        requestCameraPermission();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mSurfaceHolder = holder;
        startCameraPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        handler.removeCallbacks(runnable);
    }

    /**
     * 打开Camera
     */
    private void openCamera(SurfaceHolder surfaceHolder) {
        try {
            //设置闪关灯图片
            mIvFlash.setImageResource(R.drawable.ic_flash_auto);
            mCamera = Camera.open(CURRENT_CAMERA_FACING);
            mCameraAngle = getCameraDisplayOrientation(this, CURRENT_CAMERA_FACING);
            //设置显示角度
            mCamera.setDisplayOrientation(mCameraAngle);
            mCamera.setPreviewDisplay(surfaceHolder);

            Camera.Parameters params = mCamera.getParameters();
            Pair<Integer, Integer> size = getPreviewSize();
            params.setPictureSize(size.first, size.second);
            params.setJpegQuality(100);
            params.setPictureFormat(PixelFormat.JPEG);

            if (isSupportedFocusMode(params.getSupportedFocusModes(), Camera.Parameters.FOCUS_MODE_AUTO)) {
                //自动对焦
                params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            }
            //这句加上  在切换摄像头时会报错
//            params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
            mCamera.setParameters(params);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "摄像头打开异常", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * 判断是否支持某种聚焦模式
     *
     * @param focusList
     * @param focusMode
     * @return
     */
    private boolean isSupportedFocusMode(List<String> focusList, String focusMode) {
        for (int i = 0; i < focusList.size(); i++) {
            if (focusMode.equals(focusList.get(i))) {
                Log.i(TAG, "FocusMode supported " + focusMode);
                return true;
            }
        }
        Log.i(TAG, "FocusMode not supported " + focusMode);
        return false;
    }

    /**
     * 开启摄像头预览
     */
    private void startCameraPreview() {
        if (mCameraPreview) {
            return;
        }
        if (mCamera != null) {
            mCameraPreview = true;
            mCamera.startPreview();
            mCamera.cancelAutoFocus();
            mCamera.unlock();
            cameraReleaseEnable = true;
        }
    }

    /**
     * 切换闪光灯模式以及image 图标
     */
    private void switchFlashType() {
        switch (mTypeFlash) {
            case TYPE_FLASH_AUTO:
                mIvFlash.setImageResource(R.drawable.ic_flash_auto);
                setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                break;
            case TYPE_FLASH_ON:
                mIvFlash.setImageResource(R.drawable.ic_flash_on);
                setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                break;
            case TYPE_FLASH_OFF:
                mIvFlash.setImageResource(R.drawable.ic_flash_off);
                setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                break;
        }
    }

    /**
     * 设置闪光灯模式
     *
     * @param flashMode
     */
    private void setFlashMode(String flashMode) {
        if (mCamera == null) return;
        mCamera.lock();
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFlashMode(flashMode);
        mCamera.setParameters(parameters);
        mCamera.unlock();
    }

    /**
     * 释放摄像头
     */
    private void destroyCamera() {
        mCameraPreview = false;
        if (mCamera == null) return;
        //如果已经释放，则不用重复调用释放
        if (cameraReleaseEnable) {
            //不lock的话切换摄像头会出现异常
            mCamera.lock();
            mCamera.stopPreview();
            mSurfaceHolder.removeCallback(this);
            try {
                mCamera.setPreviewDisplay(null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mCamera.release();
        } else {
            mSurfaceHolder.removeCallback(this);
        }
        mCamera = null;
        mSurfaceHolder = null;
    }

    /**
     * 摄像头切换
     */
    private void switchCamera() {
        if (CURRENT_CAMERA_FACING == Camera.CameraInfo.CAMERA_FACING_BACK) {
            CURRENT_CAMERA_FACING = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            CURRENT_CAMERA_FACING = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        destroyCamera();
        mSurfaceHolder = mSurfaceview.getHolder();
        openCamera(mSurfaceHolder);
        startCameraPreview();
    }

    /**
     * 设置各个view的点击事件
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setViewClickListener() {
        mBtnRecord.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                startRecord();
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {
                stopRecord();
            }
            return true;
        });

        mBtnPlay.setOnClickListener(v -> playRecord());
        mBtnCancel.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(videoOutputPath)) {
                if (mType == TYPE_VIDEO) {
                    stopPlay();
                    File videoFile = new File(videoOutputPath);
                    if (videoFile.exists() && videoFile.isFile()) {
                        videoFile.delete();
                    }
                } else {
                    File imgFile = new File(imgPath);
                    if (imgFile.exists() && imgFile.isFile()) {
                        imgFile.delete();
                    }
                }
            }
            setResult(Activity.RESULT_CANCELED);
            finish();
        });

        mBtnSubmit.setOnClickListener(v -> {
            stopPlay();
            Intent intent = new Intent();
            intent.putExtra("path", videoOutputPath);
            intent.putExtra("imagePath", imgPath);
            intent.putExtra("type", mType);

            if (mType == TYPE_IMAGE && !TextUtils.isEmpty(videoOutputPath)) {
                File videoFile = new File(videoOutputPath);
                if (videoFile.exists() && videoFile.isFile()) {
                    videoFile.delete();
                }
            }
            setResult(Activity.RESULT_OK, intent);
            finish();
        });

        //摄像头切换
        mIvSwitch.setOnClickListener(v -> switchCamera());
        //闪光灯控制
        mIvFlash.setOnClickListener(v -> {
            mTypeFlash++;
            if (mTypeFlash > TYPE_FLASH_OFF) {
                mTypeFlash = TYPE_FLASH_AUTO;
            }
            switchFlashType();
        });
    }


    /**
     * 开始录制
     */
    private void startRecord() {
        timer = 0;
        if (mStartedFlag) {
            return;
        }
        mStartedFlag = true;
        //使用gone  避免visible时不可见的bug
        mLlRecordOp.setVisibility(View.GONE);
        mBtnPlay.setVisibility(View.INVISIBLE);
        mLlRecordBtn.setVisibility(View.VISIBLE);
        mProgress.setVisibility(View.VISIBLE);
        //隐藏摄像头切换
        mIvSwitch.setVisibility(View.GONE);

        handler.postDelayed(runnable, maxSec * 10L);
        recorderReleaseEnable = true;
        setUpMediaRecorder();
        startTime = System.currentTimeMillis();
    }

    /**
     * 停止录制
     */
    private void stopRecord() {
        //隐藏摄像头切换
        mIvSwitch.setVisibility(View.VISIBLE);
        if (mRecorder == null || mCamera == null) return;
        if (mStartedFlag) {
            mStartedFlag = false;
            mBtnRecord.setEnabled(false);
            mBtnRecord.setClickable(false);

            mLlRecordBtn.setVisibility(View.INVISIBLE);
            mProgress.setVisibility(View.INVISIBLE);

            handler.removeCallbacks(runnable);
            stopTime = System.currentTimeMillis();

            //录制时间过短，异常时改为拍照
            try {
                //停止录制
                mRecorder.stop();
                mRecorder.reset();
                mRecorder.release();
                recorderReleaseEnable = false;
                //锁定摄像头
                mCamera.lock();
                mCamera.stopPreview();
                mCamera.release();
                mCameraPreview = false;
                cameraReleaseEnable = false;
                //显示播放button
                mBtnPlay.setVisibility(View.VISIBLE);
                //获取视频第一帧图片(视频缩略图)
                MediaUtils.getImageForVideo(videoOutputPath, file -> {
                    imgPath = file.getAbsolutePath();
                    mLlRecordOp.setVisibility(View.VISIBLE);
                });
            } catch (Exception e) {
                e.printStackTrace();
                //出现异常时改为拍照
                takePicture();
            }
        }
    }

    /**
     * 拍照
     */
    private void takePicture() {
        mType = TYPE_IMAGE;
        mRecorder.reset();
        mRecorder.release();
        recorderReleaseEnable = false;
        mCamera.takePicture(null, null, (data, camera) -> {
            if (data != null && data.length > 0) {
                saveImage(data, result -> {
                    imgPath = result;
                    mCamera.lock();
                    mCamera.stopPreview();
                    mCamera.release();
                    mCameraPreview = false;
                    cameraReleaseEnable = false;
                    runOnUiThread(() -> {
                        mBtnPlay.setVisibility(View.INVISIBLE);
                        mLlRecordOp.setVisibility(View.VISIBLE);
                    });
                });
            }
        });
    }

    /**
     * 保存 摄像头采集的数据为 图片
     *
     * @param data
     * @param onDone
     */
    private void saveImage(byte[] data, OnStringResultCallback onDone) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int rotateAngle = 90;
                    if (mCameraAngle == 90) {
                        rotateAngle = Math.abs(mSensorAngle + mCameraAngle) % 360;
                    } else if (mCameraAngle == 270) {
                        rotateAngle = Math.abs(mCameraAngle - mSensorAngle);
                    }
                    boolean isFrontCamera = CURRENT_CAMERA_FACING == Camera.CameraInfo.CAMERA_FACING_FRONT;
                    if (isFrontCamera) {
                        rotateAngle = 360 - rotateAngle;
                    }
                    String imgFileName = "IMG_" + getDate() + ".jpg";
                    File imgFile = new File(dirPath + File.separator + imgFileName);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    Bitmap newBitmap = PictureUtils.rotateBitmap(bitmap, rotateAngle, isFrontCamera);
                    imgFile.createNewFile();

                    BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(imgFile));
                    newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.flush();
                    os.close();
                    int degree = PictureUtils.getBitmapDegree(imgFile.getAbsolutePath());
                    Log.d(TAG, "图片角度为：" + degree);
                    //回调结果
                    if (onDone != null) {
                        onDone.onResult(imgFile.getAbsolutePath());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 播放视频
     */
    private void playRecord() {
        if (cameraReleaseEnable) {
            mCamera.lock();
            mCamera.stopPreview();
            mCamera.release();
            mCameraPreview = false;
            cameraReleaseEnable = false;
        }
        playerReleaseEnable = true;
        mPlayFlag = true;
        mBtnPlay.setVisibility(View.INVISIBLE);
        mMediaPlayer.reset();

        Uri uri = Uri.parse(videoOutputPath);
        try {
            mMediaPlayer.setDataSource(this, uri);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDisplay(mSurfaceHolder);
            mMediaPlayer.setOnCompletionListener(mp -> mBtnPlay.setVisibility(View.VISIBLE));

            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止播放视频
     */
    private void stopPlay() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
    }


    /**
     * MediaRecorder的启动配置
     */
    private void setUpMediaRecorder() {
        final int nowAngle = (mSensorAngle + 90) % 360;
        mRecorder = new MediaRecorder();
        mRecorder.reset();
        mRecorder.setCamera(mCamera);
        // 设置音频源与视频源 这两项需要放在setOutputFormat之前;
        mRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //设置输出格式
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        //这两项需要放在setOutputFormat之后 IOS必须使用ACC
        //音频编码格式
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //使用MPEG_4_SP格式在华为P20 pro上停止录制时会出现
        //MediaRecorder: stop failed: -1007
        //java.lang.RuntimeException: stop failed.
        // at android.media.MediaRecorder.stop(Native Method)
        mRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        //设置最终出片分辨率
        mRecorder.setVideoSize(640, 480);
        mRecorder.setVideoFrameRate(30);
        mRecorder.setVideoEncodingBitRate(3 * 1024 * 1024);
        int orientationHint = getRecorderOrientationHint(nowAngle);
        mRecorder.setOrientationHint(orientationHint);
        //设置记录会话的最大持续时间（毫秒）
        mRecorder.setMaxDuration(maxSec * 1000);
        //获取视频输入文件名
        videoOutputPath = getMediaRecorderOutputFilePath();
        if (TextUtils.isEmpty(videoOutputPath)) {
            Toast.makeText(this, "视频输出路劲为空", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        //设置输入文件路劲
        mRecorder.setOutputFile(videoOutputPath);
        try {
            mRecorder.prepare();
            //开始录制
            mRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得当前要设置recoder 的OrientationHint角度
     *
     * @param nowAngle
     * @return
     */
    private int getRecorderOrientationHint(int nowAngle) {
        int orientationHint = nowAngle;
        if (CURRENT_CAMERA_FACING == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            //手机预览倒立的处理
            if (mCameraAngle == 270) {
                //横屏
                if (nowAngle == 0) {
                    orientationHint = 180;
                } else if (nowAngle == 270) {
                    orientationHint = 270;
                } else {
                    orientationHint = 90;
                }
            } else {
                if (nowAngle == 90) {
                    orientationHint = 270;
                } else if (nowAngle == 270) {
                    orientationHint = 90;
                } else {
                    orientationHint = nowAngle;
                }
            }
        } else {
            orientationHint = nowAngle;
        }
        return orientationHint;
    }

    /**
     * 获得录制视频输出保存的文件名
     *
     * @return
     */
    private String getMediaRecorderOutputFilePath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            String rootPath = videoOutputPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "MtiVideoRecorder";
            //检查目录是否存在，不存在则创建
            File dir = new File(rootPath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            dirPath = dir.getAbsolutePath();
            //最终视频的文件全路劲
            videoOutputPath = dir.getAbsolutePath() + "/" + getDate() + ".mp4";
            Log.d(TAG, "文件路径：" + videoOutputPath);
        }
        return videoOutputPath;
    }

    /**
     * 获得当前屏幕最佳预览大小
     *
     * @return
     */
    private Pair<Integer, Integer> getPreviewSize() {
        int bestPreviewWidth = 1920;
        int bestPreviewHeight = 1080;
        int mCameraPreviewWidth;
        int mCameraPreviewHeight;
        int diffs = Integer.MAX_VALUE;
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point screenResolution = new Point(display.getWidth(), display.getHeight());
        List<Camera.Size> availablePreviewSizes = mCamera.getParameters().getSupportedPreviewSizes();
        Log.e(TAG, "屏幕宽度" + screenResolution.x + "屏幕高度" + screenResolution.y);
        for (Camera.Size previewSize : availablePreviewSizes) {
            mCameraPreviewWidth = previewSize.width;
            mCameraPreviewHeight = previewSize.height;
            int newDiffs = Math.abs(mCameraPreviewWidth - screenResolution.y) + Math.abs(mCameraPreviewHeight - screenResolution.x);
            Log.v(TAG, "newDiffs=" + newDiffs);
            if (newDiffs == 0) {
                bestPreviewWidth = mCameraPreviewWidth;
                bestPreviewHeight = mCameraPreviewHeight;
                break;
            }
            if (diffs > newDiffs) {
                bestPreviewWidth = mCameraPreviewWidth;
                bestPreviewHeight = mCameraPreviewHeight;
                diffs = newDiffs;
            }
            Log.e(TAG, previewSize.width + "--" + previewSize.height + "宽度" + bestPreviewWidth + "高度" + bestPreviewHeight);
        }
        Log.e(TAG, "最佳宽度" + bestPreviewWidth + "最佳高度" + bestPreviewHeight);
        return new Pair<>(bestPreviewWidth, bestPreviewHeight);
    }

    /**
     * 计算获得摄像头 旋转的角度
     *
     * @param context
     * @param cameraId
     * @return
     */
    private int getCameraDisplayOrientation(Context context, int cameraId) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int rotation = wm.getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }
        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;   // compensate the mirror
        } else {
            // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }


    private void registerSensorManager(Context context) {
        if (mSensorManager == null) {
            mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        }
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager
                .SENSOR_DELAY_NORMAL);
    }

    private void unregisterSensorManager(Context context) {
        if (mSensorManager == null) {
            mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        }
        mSensorManager.unregisterListener(sensorEventListener);
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent event) {
            if (Sensor.TYPE_ACCELEROMETER != event.sensor.getType()) {
                return;
            }
            float[] values = event.values;
            mSensorAngle = AngleUtil.getSensorAngle(values[0], values[1]);
            //可根据角度进行图标的旋转
//            rotationAnimation();
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };


    /**
     * 获取系统时间
     *
     * @return
     */
    private String getDate() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);           // 获取年份
        int month = ca.get(Calendar.MONTH);        // 获取月份
        int day = ca.get(Calendar.DATE);            // 获取日
        int minute = ca.get(Calendar.MINUTE);     // 分
        int hour = ca.get(Calendar.HOUR);          // 小时
        int second = ca.get(Calendar.SECOND);     // 秒
        return "" + year + (month + 1) + day + hour + minute + second;
    }

    private void initViews() {
        mProgress = findViewById(R.id.mProgress);
        mSurfaceview = findViewById(R.id.mSurfaceview);
        mBtnPlay = findViewById(R.id.mBtnPlay);
        mBtnRecord = findViewById(R.id.mBtnRecord);
        mTvRecordTip = findViewById(R.id.mTvRecordTip);
        mLlRecordBtn = findViewById(R.id.mLlRecordBtn);
        mBtnCancel = findViewById(R.id.btnCancel);
        mBtnSubmit = findViewById(R.id.btnSubmit);
        mLlRecordOp = findViewById(R.id.mLlRecordOp);
        mIvFlash = findViewById(R.id.ivFlash);
        mIvSwitch = findViewById(R.id.ivSwitch);
        mLayoutTopRight = findViewById(R.id.layoutTopRight);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //销毁传感器监听
        unregisterSensorManager(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mPlayFlag) {
            stopPlay();
        }
        if (mStartedFlag) {
            stopRecord();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mRecorder && recorderReleaseEnable) {
            mRecorder.release();
        }
        destroyCamera();
        if (null != mMediaPlayer && playerReleaseEnable) {
            mMediaPlayer.release();
        }
        mRecorder = null;
        mMediaPlayer = null;
    }

    /**
     * 权限申请
     */
    private void requestCameraPermission() {
        if (hasCameraPermission()) {
            //直接执行
            openCamera(mSurfaceHolder);
            if (!mCameraPreview) {
                startCameraPreview();
            }
        } else {
            //申请权限
            ActivityCompat.requestPermissions(this, permission, 100);
        }
    }

    /**
     * 检查是否已经有全部权限
     *
     * @return
     */
    private boolean hasCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int noPermissionCount = 0;
            for (String p : permission) {
                int result = ContextCompat.checkSelfPermission(this, p);
                if (result != PackageManager.PERMISSION_GRANTED) {
                    noPermissionCount++;
                }
            }
            return noPermissionCount == 0;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            int successCount = 0;
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {
                    successCount++;
                }
            }
            if (successCount == permissions.length) {
                //全部申请成功
                openCamera(mSurfaceHolder);
                if (!mCameraPreview) {
                    startCameraPreview();
                }
            } else {
                //有失败的权限
                finish();
            }
        }
    }

    public interface OnStringResultCallback {
        void onResult(String result);
    }
}
