package com.mti.component.media.audio.internal;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;

public class AudioFileFunc {
    //音频输入-麦克风
    public final static int AUDIO_INPUT = MediaRecorder.AudioSource.MIC;

    //采用频率
    //44100是目前的标准，但是某些设备仍然支持22050，16000，11025
    public final static int AUDIO_SAMPLE_RATE = 44100;  //44.1KHz,普遍使用的频率   
    //录音输出文件
    private final static String AUDIO_RAW_FILENAME = "disp_chat_audio.raw";
    private final static String AUDIO_WAV_FILENAME = "disp_chat_audio.wav";
    public final static String AUDIO_AMR_FILENAME = "disp_chat_audio.amr";

    /**
     * 判断是否有外部存储设备sdcard
     *
     * @return true | false
     */
    public static boolean isSdcardExit() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 获取麦克风输入的原始音频流文件路径
     *
     * @return
     */
    public static String getRawFilePath(Context context) {
        String mAudioRawPath = "";
        if (isSdcardExit()) {

            String fileBasePath = getAudioDir(context);
            //  String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mAudioRawPath = fileBasePath + "/" + AUDIO_RAW_FILENAME;
        }

        return mAudioRawPath;
    }

    /**
     * 获取编码后的WAV格式音频文件路径
     *
     * @return
     */
    public static String getWavFilePath(Context context) {
        String mAudioWavPath = "";
        if (isSdcardExit()) {
            String fileBasePath = getAudioDir(context);
            //   String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mAudioWavPath = fileBasePath + "/" + AUDIO_WAV_FILENAME;
        }
        return mAudioWavPath;
    }


    /**
     * 获取编码后的AMR格式音频文件路径
     *
     * @return
     */
    public static String getAMRFilePath(Context context) {
        String mAudioAMRPath = "";
        if (isSdcardExit()) {
            String fileBasePath = getAudioDir(context);
            //  String fileBasePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            mAudioAMRPath = fileBasePath + "/" + AUDIO_AMR_FILENAME;
        }
        return mAudioAMRPath;
    }


    /**
     * 获取文件大小
     *
     * @param path,文件的绝对路径
     * @return
     */
    public static long getFileSize(String path) {
        File mFile = new File(path);
        if (!mFile.exists())
            return -1;
        return mFile.length();
    }


    /**
     * 获取音频存储路径，/Android/data/包名/cache/audio
     *
     * @param context
     * @return
     */
    public static String getAudioDir(Context context) {
        return createDir(context, "audio");
    }


    /**
     * 从/Android/data/包名/cache 目录 获取指定文件夹名称 的目录地址
     *
     * @param context
     * @param dirName 目录名
     * @return
     */
    private static String createDir(Context context, String dirName) {
        String cacheDir = getProjectCacheDir(context);
        String dir = cacheDir + File.separator + dirName;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 获得外部存储卡 /Android/data/包名/cache 目录
     * 这个目录下的文件 在应用卸载时一并会删除
     *
     * @param context
     * @return
     */
    private static String getProjectCacheDir(Context context) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            throw new RuntimeException("没有存储卡");
        }
        return context.getExternalCacheDir().getAbsolutePath();
    }
}