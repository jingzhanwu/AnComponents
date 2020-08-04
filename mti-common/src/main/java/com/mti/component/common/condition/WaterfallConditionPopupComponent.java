package com.mti.component.common.condition;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.mti.component.common.R;
import com.mti.component.common.entry.ScreenGroup;
import com.mti.component.common.entry.TagEntry;
import com.mti.component.common.tag.FlowComponent;
import com.mti.component.common.tag.OnTagClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2019/7/16
 * @change
 * @describe 商品下拉筛选
 **/
public class WaterfallConditionPopupComponent extends PartShadowPopupView implements OnTagClickListener {


    private static final String TAG = "WaterfallDrawerPopupComponent";

    private LinearLayout contentLayout;

    private List<ScreenGroup> originalScreenGroups = new ArrayList<>();
    private List<ScreenGroup> screenGroups;
    /**
     * 筛选操作监听
     */
    private OnWaterFallChangedListener waterFallChangedListener;

    public interface OnWaterFallChangedListener {
        /**
         * 操作了某个item
         *
         * @param position
         * @param entry
         */
         void onItemClickChanged(int position, TagEntry entry);

        /**
         * 选中状态法生改变，即单选，多选发生改变
         *
         * @param position
         * @param entry
         */
        void onItemCheckedChanged(int position, TagEntry entry);
    }

    public WaterfallConditionPopupComponent(@NonNull Context context, List<ScreenGroup> originalScreenGroups) {
        super(context);
        this.originalScreenGroups = originalScreenGroups;
        copy();
    }


    /**
     * 拷贝数据
     */
    private void copy() {
        try {

            if (screenGroups==null){
                screenGroups=new ArrayList<>();
            }
            screenGroups.clear();
            for (int i = 0; i < originalScreenGroups.size(); i++) {
                ScreenGroup group = (ScreenGroup) originalScreenGroups.get(i).deepClone();

                Log.d("screen---","group--"+group);
                screenGroups.add(group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addWaterFallChangedListener(OnWaterFallChangedListener waterFallChangedListener) {
        this.waterFallChangedListener = waterFallChangedListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_part_shadow_condition;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        contentLayout = findViewById(R.id.screenContentLayout);
        findViewById(R.id.tvReset).setOnClickListener(v -> reset());

        findViewById(R.id.tvConfirm).setOnClickListener(v -> dismiss());
        initDataAndUi();
    }


    /**
     * 创建布局
     *
     * @param group
     */
    private void buildCommonItemLayout(ScreenGroup group) {
        LinearLayout itemChild = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_screen_condition, null);

        TextView tvSubTitle = itemChild.findViewById(R.id.tvSubTitle);
        ImageView ivMore = itemChild.findViewById(R.id.ivMore);
        ivMore.setOnClickListener(v -> {

        });
        FlowComponent component = itemChild.findViewById(R.id.tagComponent);
        tvSubTitle.setText(group.getTitle());
        component.setGroupName(group.getTitle());
        component.setGroupId(group.getKey());
        component.setOptional(group.isOptional());
        component.setMultiple(group.isMultiple());
        component.setShowingDelete(group.isShowingDelete());
        component.initAddListener(group.getTags(), this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        contentLayout.addView(itemChild, params);
    }

    /**
     * 初始化数据ui
     */
    private void initDataAndUi() {
        contentLayout.removeAllViews();

        if (screenGroups == null || screenGroups.size() == 0) {
            return;
        }
        for (int i = 0; i < screenGroups.size(); i++) {
            buildCommonItemLayout(screenGroups.get(i));
        }
    }


    /**
     * 重置数据状态
     */
    private void reset() {
        copy();
        initDataAndUi();
    }

    @Override
    public void onItemClick(int position, TagEntry entry) {
        if (waterFallChangedListener == null) {
            return;
        }
        waterFallChangedListener.onItemClickChanged(position, entry);
    }


    @Override
    public void onItemChanged(int position, TagEntry tagEntry) {
        if (waterFallChangedListener == null) {
            return;
        }
        waterFallChangedListener.onItemCheckedChanged(position, tagEntry);
    }

    /**
     * 获取选中的数据
     *
     * @return
     */
    public List<ScreenGroup> getSelections() {
        List<ScreenGroup> selections = new ArrayList<>();
        int childCount = contentLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            LinearLayout child = (LinearLayout) contentLayout.getChildAt(i);
            FlowComponent component = child.findViewById(R.id.tagComponent);
            selections.add(new ScreenGroup()
                    .title(component.getGroupName())
                    .key(component.getGroupId())
                    .tags(component.getSelections()));

        }
        return selections;
    }
}
