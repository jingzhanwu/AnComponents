package com.mti.component.common.dial;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mti.component.common.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.shaohui.bottomdialog.BottomDialog;


/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2018/10/16
 * @change
 * @describe describe
 * 拨号盘工具类
 **/
public class DialPlateHelper {

    /**
     * 添加号码文本
     */
    private List<String> texts = new ArrayList<>();

    /**
     * 视频点击监听
     */
    private OnVideoClickListener onVideoClickListener;
    /**
     * 音频点击监听
     */
    private OnVoiceCLickListener onVoiceCLickListener;

    /**
     * 显示底部操作栏
     */
    private boolean showingBottomBar=true;
    /**
     * 左边图标
     */
    private @DrawableRes int leftResId;
    /**
     * 右边图标
     */
    private @DrawableRes int rightResId;
    /**
     * 最大字符数
     */
    private int maxCharacters=11;

    /**
     * 视频点击监听
     */
    public interface OnVideoClickListener {
        void onVideoCLick(String number);
    }

    /**
     * 语音点击监听
     */
    public interface OnVoiceCLickListener {
        void onVoiceCLick(String number);
    }

    private DialPlateHelper() {
        texts.clear();
        initDialPlateContent();
    }

    /**
     * 初始化自定义键盘数据
     */
    private void initDialPlateContent() {
        for (int i = 1; i < 10; i++) {
            texts.add(i + "");
        }

        texts.addAll(Arrays.asList("#", "0", "*"));
    }


    public static DialPlateHelper create() {
        return new DialPlateHelper();
    }

    /**
     * 添加语音点击监听
     *
     * @param onVoiceCLickListener
     */
    public DialPlateHelper addOnVoiceCLickListener(OnVoiceCLickListener onVoiceCLickListener) {
        this.onVoiceCLickListener = onVoiceCLickListener;
        return this;
    }

    /**
     * 添加视频点击监听
     *
     * @param onVideoClickListener
     */
    public DialPlateHelper addOnVideoClickListener(OnVideoClickListener onVideoClickListener) {
        this.onVideoClickListener = onVideoClickListener;
        return this;
    }

    public boolean isShowingBottomBar() {
        return showingBottomBar;
    }

    public void setShowingBottomBar(boolean showingBottomBar) {
        this.showingBottomBar = showingBottomBar;
    }


    public int getLeftResId() {
        return leftResId;
    }

    public void setLeftResId(int leftResId) {
        this.leftResId = leftResId;
    }

    public int getRightResId() {
        return rightResId;
    }

    public void setRightResId(int rightResId) {
        this.rightResId = rightResId;
    }

    public void setMaxCharacters(int maxCharacters) {
        this.maxCharacters = maxCharacters;
    }

    public int getMaxCharacters() {
        return maxCharacters;
    }

    /**
     * 创建拨号盘
     */
    public void createDialPlate(FragmentManager fragmentManager) {
        BottomDialog.create(fragmentManager)
                .setViewListener(v -> initDialPlateLayout(v))
                .setLayoutRes(R.layout.pop_dial)
                // Dialog window dim amount(can change window background color）, range：0 to 1，default is : 0.2f
                .setDimAmount(0.65f)
                // click the external area whether is closed, default is : true
                .setCancelOutside(true)
                .setTag("BottomDialog")
                .show();
    }


    /**
     * 初始化布局
     */
    private void initDialPlateLayout(View v) {
        ChipLayout chipLayout = v.findViewById(R.id.chipLayout);
        LinearLayout llVideo = v.findViewById(R.id.llVideo);
        LinearLayout llVoice = v.findViewById(R.id.llVoice);
        TextView tvKeyBoardNumber = v.findViewById(R.id.tvKeyBoardNumber);
        ImageView ivDel = v.findViewById(R.id.ivDel);
        ImageView ivLeft = v.findViewById(R.id.ivLeft);
        ImageView ivRight = v.findViewById(R.id.ivRight);
        LinearLayout bottomBarLayout = v.findViewById(R.id.bottomBarLayout);

        bottomBarLayout.setVisibility(showingBottomBar?View.VISIBLE:View.GONE);

        if (leftResId!=0){
            ivLeft.setImageDrawable(ivLeft.getContext().getDrawable(leftResId));
        }

        if (rightResId!=0){
            ivRight.setImageDrawable(ivRight.getContext().getDrawable(rightResId));
        }

        StringBuffer stringBuffer = new StringBuffer();
        chipLayout.addChipList(texts, position -> {
            vibrator(v);
            if (stringBuffer.length() == maxCharacters) {
                Toast.makeText(v.getContext(),"最多可输入"+maxCharacters+"位",Toast.LENGTH_LONG).show();
                return;
            }
            String numStr = stringBuffer.append(texts.get(position)).toString();
            tvKeyBoardNumber.setText(numStr);
        });

        ivDel.setOnClickListener(view -> {
            vibrator(view);
            if (stringBuffer.length() == 0) {
                tvKeyBoardNumber.setText("");
                return;
            }
            String deleteCharAt = stringBuffer.deleteCharAt(stringBuffer.length() - 1).toString();
            tvKeyBoardNumber.setText(deleteCharAt);
        });


        ivDel.setOnLongClickListener(v1 -> {
            vibrator(v1);
            if (stringBuffer.length() == 0) {
                tvKeyBoardNumber.setText("");

            }else {
                String deleteCharAt = stringBuffer.delete(0,stringBuffer.length()).toString();
                tvKeyBoardNumber.setText(deleteCharAt);
            }
            return false;
        });

        llVideo.setOnClickListener(view -> {
            if (onVideoClickListener == null) {
                return;
            }
            onVideoClickListener.onVideoCLick(tvKeyBoardNumber.getText().toString());
        });

        llVoice.setOnClickListener(view -> {
            if (onVoiceCLickListener == null) {
                return;
            }
            onVoiceCLickListener.onVoiceCLick(tvKeyBoardNumber.getText().toString());
        });
    }

    /**
     * 震动
     */
    private void vibrator(View view) {
        Context context = view.getContext();
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(100);
    }


}
