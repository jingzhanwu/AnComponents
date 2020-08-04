package com.mti.component.common.tag;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.mti.component.common.R;
import com.mti.component.common.entry.ScreenGroup;
import com.mti.component.common.entry.TagEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by 薛兵
 * @date 2020/5/27
 * @change
 * @describe 流式包布局 （RecyclerView方式）
 **/
public class FlowComponent extends LinearLayout {

    private FlowTagAdapter flowTagAdapter;
    private RecyclerView recyclerView;


    /**
     * 删除图片
     */
    private @DrawableRes int deleteResId;
    /**
     * 文本颜色
     */
    private int fontColor;
    /**
     * 文本大小
     */
    private float fontSize;

    /**
     * 是否显示删除
     */
    private boolean showingDelete=true;

    /**
     * 是否可选择
     */
    private boolean optional;
    /**
     * 是否多选
     */
    private boolean isMultiple;

    /**
     * 背景选择器
     */
    private int backgroundSelectorRes= R.drawable.label_tag_selector;

    /**
     * 数据组
     */
    private ScreenGroup group;
    /**
     * 组名称
     */
    private String groupName;
    /**
     * 组id
     */
    private String groupId;
    public FlowComponent(Context context) {
        this(context, null);
    }

    public FlowComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_flow_layout, this);
        recyclerView = findViewById(R.id.recyclerView);
    }


    public int getDeleteResId() {
        return deleteResId;
    }

    public void setDeleteResId(int deleteResId) {
        this.deleteResId = deleteResId;
    }

    public int getFontColor() {
        return fontColor;
    }

    public void setFontColor(int fontColor) {
        this.fontColor = fontColor;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isShowingDelete() {
        return showingDelete;
    }

    public void setShowingDelete(boolean showingDelete) {
        this.showingDelete = showingDelete;
    }

    public boolean isMultiple() {
        return isMultiple;
    }

    public void setMultiple(boolean multiple) {
        isMultiple = multiple;
    }

    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    public int getBackgroundSelectorRes() {
        return backgroundSelectorRes;
    }

    public void setBackgroundSelectorRes(int backgroundSelectorRes) {
        this.backgroundSelectorRes = backgroundSelectorRes;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    /**
     * 初始化并设置监听
     *
     * @param listener
     */
    public void initAddListener(OnTagClickListener listener) {
        initAddListener(new ArrayList(), listener);
    }


    public TagEntry getItemEntry(int position){
        return flowTagAdapter.getItemEntry(position);
    }

    /**
     * 初始化并设置监听
     *
     * @param listener
     */
    public void initAddListener(List<TagEntry> data, OnTagClickListener listener) {
        flowTagAdapter = new FlowTagAdapter(getContext(), data);
        flowTagAdapter.setBackgroundSelectorRes(backgroundSelectorRes);
        flowTagAdapter.setDeleteResId(deleteResId);
        flowTagAdapter.setFontColor(fontColor);
        flowTagAdapter.setFontSize(fontSize);
        flowTagAdapter.setShowingDelete(showingDelete);

        //初始化recycler
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        //flexDirection 属性决定主轴的方向（即项目的排列方向）。类似 LinearLayout 的 vertical 和 horizontal。
        //主轴为水平方向，起点在左端。
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        //flexWrap 默认情况下 Flex 跟 LinearLayout 一样，都是不带换行排列的，但是flexWrap属性可以支持换行排列。
        //按正常方向换行
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        //justifyContent 属性定义了项目在主轴上的对齐方式。
        //交叉轴的起点对齐。
        flexboxLayoutManager.setJustifyContent(JustifyContent.FLEX_START);

        recyclerView.setLayoutManager(flexboxLayoutManager);
        recyclerView.setAdapter(flowTagAdapter);

        flowTagAdapter.setOnItemClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(int position,TagEntry tagEntry) {
                if (listener==null){
                    return;

                }
                listener.onItemClick(position,tagEntry);

                if (!optional){
                    return;
                }

                if (isMultiple){
                    flowTagAdapter.selectByMultipleChoice(position);
                }else {
                    flowTagAdapter.selectBySingleChoice(position);
                }

                listener.onItemChanged(position,tagEntry);
            }

            @Override
            public void onItemDelete(int position,TagEntry tagEntry) {
                if (listener==null){
                    return;

                }
                listener.onItemDelete(position,tagEntry);
            }
        });
    }



    public void setNewData(List<TagEntry> list) {
        flowTagAdapter.setNewData(list);
    }

    /**
     * 添加数据
     *
     * @param tagEntry
     */
    public void addData(TagEntry tagEntry) {
        flowTagAdapter.addTag(tagEntry);
    }

    /**
     * 删除
     *
     * @param pos
     */
    public void deleteData(int pos) {
        flowTagAdapter.deleteTag(pos);
    }


    public List<TagEntry> getSelections(){
        return flowTagAdapter.getSelections();
    }
}
