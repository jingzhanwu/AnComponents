package com.mti.component.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mti.component.common.util.DisplayTool;

/**
 * @anthor created by jzw
 * @date 2020/5/20
 * @change
 * @describe 多功能输入显示框组件
 * 支持左右文字label，小图标
 * 背景色定制
 * 字体大小
 * 字体颜色
 **/
public class InputComponent extends RelativeLayout {
    /**
     * 左边的必选标记
     */
    private TextView tvFlag;
    /**
     * 左边的文本label
     */
    private TextView tvLabel;
    /**
     * 输入框左边的图标
     */
    private ImageView ivLeft;
    /**
     * 输入框右边的图标
     */
    private ImageView ivRight;
    /**
     * 输入框
     */
    private EditText etInput;
    /**
     * 文本显示框
     */
    private TextView tvText;
    /**
     * 整个右边部分的容器view
     */
    private View layoutRight;

    /**
     * 默认字体大小，单位为px
     */
    private float mDefaultTextSize;
    /**
     * 默认的输入框字体颜色
     */
    private int mDefaultInputTextColor;

    //是否显示左右两边的图标
    private boolean showLeftImage = false;
    private boolean showRightImage = false;
    //是否打开编辑模式
    private boolean openEdit = false;
    //是否显示左边的标记view
    private boolean showMarker = false;
    //是否显示左边的label
    private boolean showLabel = true;
    //禁止输入
    private boolean mDisableInput = false;
    /**
     * 输入框区域，默认高度
     */
    private int mInputDefaultHeight;

    public InputComponent(Context context) {
        this(context, null);
    }

    public InputComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 初始化组件属性
     *
     * @param context
     * @param attrs
     */
    @SuppressLint("ResourceAsColor")
    private void init(Context context, @Nullable AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.input_component, this);
        tvFlag = findViewById(R.id.tvFlag);
        tvLabel = findViewById(R.id.tvLabel);
        ivLeft = findViewById(R.id.ivLeft);
        ivRight = findViewById(R.id.ivRight);
        etInput = findViewById(R.id.etInput);
        tvText = findViewById(R.id.tvText);
        layoutRight = findViewById(R.id.layoutRight);
        //初始化默认字体大小
        mDefaultTextSize = DisplayTool.dip2px(context, 14);
        //初始化默认字体颜色
        mDefaultInputTextColor = R.color.black_content;
        mInputDefaultHeight = DisplayTool.dip2px(context, 42);
        if (attrs != null) {
            TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.InputComponent);
            //左边文本
            String labelText = attr.getString(R.styleable.InputComponent_labelText);
            //label颜色
            int labelTextColor = attr.getColor(R.styleable.InputComponent_labelTextColor, mDefaultInputTextColor);
            //label字体大小
            float labelTextSize = attr.getDimension(R.styleable.InputComponent_labelTextSize, mDefaultTextSize);

            //默认的显示文本，与hint文本
            String defaultInputText = attr.getString(R.styleable.InputComponent_defaultText);
            String defaultHintText = attr.getString(R.styleable.InputComponent_inputHintText);

            //输入框与Text的字体颜色
            int inputTextColor = attr.getColor(R.styleable.InputComponent_inputTextColor, mDefaultInputTextColor);
            //输入框与Text的字体大小
            float inputTextSize = attr.getDimension(R.styleable.InputComponent_inputTextSize, mDefaultTextSize);

            //整个右边输入区域的背景
            int backgroundRes = attr.getResourceId(R.styleable.InputComponent_inputBackground, R.drawable.gray_4radius_shape);

            //左边和右边图标资源
            Drawable leftIvDrawable = attr.getDrawable(R.styleable.InputComponent_leftImage);
            Drawable rightIvDrawable = attr.getDrawable(R.styleable.InputComponent_rightImage);

            //是否显示左右两边的图标
            showLeftImage = attr.getBoolean(R.styleable.InputComponent_showLeftImage, false);
            showRightImage = attr.getBoolean(R.styleable.InputComponent_showRightImage, false);
            //是否打开编辑模式
            openEdit = attr.getBoolean(R.styleable.InputComponent_openEdit, false);
            //是否显示左边的标记view
            showMarker = attr.getBoolean(R.styleable.InputComponent_showMarker, false);
            //是否显示左边的label
            showLabel = attr.getBoolean(R.styleable.InputComponent_showLabel, true);
            //禁止输入
            mDisableInput = attr.getBoolean(R.styleable.InputComponent_disableInput, false);

            //右边输入区域整体最小高度
            float height = attr.getDimension(R.styleable.InputComponent_inputMinHeight, 0);
            if (height > 0) {
                layoutRight.setMinimumHeight((int) height);
                tvText.setMaxLines(Integer.MAX_VALUE);
                tvText.setEllipsize(null);

                etInput.setMaxLines(Integer.MAX_VALUE);
                etInput.setEllipsize(null);
                if (height > mInputDefaultHeight) {
                    tvText.setGravity(Gravity.LEFT | Gravity.TOP);
                    etInput.setGravity(Gravity.LEFT | Gravity.TOP);
                }
            }
            setLabel(labelText);
            setLabelTextColor(labelTextColor);
            setLabelTextSize(DisplayTool.px2sp(context, labelTextSize));

            setInputText(defaultInputText);
            setInputHintText(defaultHintText);
            setInputTextColor(inputTextColor);
            setInputTextSize(DisplayTool.px2sp(context, inputTextSize));

            setLeftImageDrawable(leftIvDrawable);
            setRightImageDrawable(rightIvDrawable);

            setInputBackground(backgroundRes);

            attr.recycle();
        }

        showLeftImage(showLeftImage);
        showRightImage(showRightImage);
        openEditMode(openEdit);
        showMarker(showMarker);
        showLabel(showLabel);
        disableInput(mDisableInput);
    }

    /**
     * 设置左边文本
     *
     * @param label
     */
    public void setLabel(String label) {
        tvLabel.setText(label);
    }

    /**
     * 设置左边文本颜色
     *
     * @param color
     */
    public void setLabelTextColor(@ColorInt int color) {
        tvLabel.setTextColor(color);
    }

    /**
     * 设置左边文本字体大小
     *
     * @param sizeSP
     */
    public void setLabelTextSize(float sizeSP) {
        tvLabel.setTextSize(sizeSP);
    }

    /**
     * 设置输入/显示框的文本内容
     *
     * @param text
     */
    public void setInputText(String text) {
        etInput.setText(text);
        tvText.setText(text);
    }

    /**
     * 设置输入/显示框的hint文本内容
     *
     * @param text
     */
    public void setInputHintText(String text) {
        etInput.setHint(text);
        tvText.setHint(text);
    }

    /**
     * 设置输入/显示框的字体颜色
     *
     * @param color
     */
    public void setInputTextColor(@ColorInt int color) {
        etInput.setTextColor(color);
        tvText.setTextColor(color);
    }

    /**
     * 设置输入/显示框的字体大小
     *
     * @param sizeSP
     */
    public void setInputTextSize(float sizeSP) {
        etInput.setTextSize(sizeSP);
        tvText.setTextSize(sizeSP);
    }

    /**
     * 设置左边image的图片
     *
     * @param drawable
     */
    public void setLeftImageDrawable(Drawable drawable) {
        ivLeft.setImageDrawable(drawable);
    }

    /**
     * 设置右边image的图片
     *
     * @param drawable
     */
    public void setRightImageDrawable(Drawable drawable) {
        ivRight.setImageDrawable(drawable);
    }

    /**
     * 是否显示左边的图标
     *
     * @param show
     */
    public void showLeftImage(boolean show) {
        ivLeft.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 是否显示右边的图标
     *
     * @param show
     */
    public void showRightImage(boolean show) {
        ivRight.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 是否打开编辑模式
     *
     * @param edit
     */
    public void openEditMode(boolean edit) {
        if (edit) {
            etInput.setVisibility(View.VISIBLE);
            tvText.setVisibility(View.INVISIBLE);
        } else {
            etInput.setVisibility(View.INVISIBLE);
            tvText.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 是否显示左边的标记view
     *
     * @param show
     */
    public void showMarker(boolean show) {
        tvFlag.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 是否显示左边label
     *
     * @param show
     */
    public void showLabel(boolean show) {
        tvLabel.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置整个右边区域的背景
     *
     * @param resId
     */
    public void setInputBackground(@DrawableRes int resId) {
        layoutRight.setBackgroundResource(resId);
    }

    public void setInputBackground(Drawable drawable) {
        layoutRight.setBackground(drawable);
    }

    /**
     * 禁止输入
     *
     * @param disableInput
     */
    public void disableInput(boolean disableInput) {
        etInput.setEnabled(!disableInput);
        tvText.setEnabled(!disableInput);
    }

    /**
     * 返回是否是编辑模式
     *
     * @return
     */
    public boolean isOpenEditMode() {
        return openEdit;
    }

    /**
     * 返回当前输入框的内容
     *
     * @return
     */
    public String getInputText() {
        if (openEdit) {
            return etInput.getText().toString();
        } else {
            return tvText.getText().toString();
        }
    }

    /**
     * 左边image的点击事件
     *
     * @param clickListener
     */
    public void setOnLeftImageClickListener(OnClickListener clickListener) {
        ivLeft.setOnClickListener(clickListener);
    }

    /**
     * 右边image的点击事件
     *
     * @param clickListener
     */
    public void setOnRightImageClickListener(OnClickListener clickListener) {
        ivRight.setOnClickListener(clickListener);
    }

    /**
     * 整个右边输入区域添加点击事件
     *
     * @param clickListener
     */
    public void setOnInputAreaClickListener(OnClickListener clickListener) {
        layoutRight.setOnClickListener(clickListener);
    }
}
