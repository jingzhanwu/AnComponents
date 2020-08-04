package com.mti.component.master.condition;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.lxj.xpopup.impl.PartShadowPopupView;
import com.mti.component.master.R;
import com.mti.component.master.adapter.SimpleConditionAdapter;
import com.mti.component.master.model.ConditionEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @company 上海道枢信息科技-->
 * @anthor created by xuebing
 * @date 2019/7/16
 * @change
 * @describe 简单下来筛选菜单
 **/
public class SimpleWaterfallConditionPopup extends PartShadowPopupView implements SimpleConditionAdapter.OnConditionItemClickListener {


    private static final String TAG = "SimpleWaterfallConditionPopup";

    private Context mContext;
    private List<ConditionEntry> originalData = new ArrayList<>();
    private List<ConditionEntry> data;
    private SimpleConditionAdapter mAdapter;
    /**
     * 筛选操作监听
     */
    private OnSimpleConditionChangedListener waterFallChangedListener;

    @Override
    public void onConditionItemClick(int pos, ConditionEntry entry) {

        if (mAdapter != null) {
            mAdapter.single(pos);
        }

        if (waterFallChangedListener == null) {
            return;
        }
        waterFallChangedListener.onItemClickChanged(pos, entry);
        dismiss();
    }

    public interface OnSimpleConditionChangedListener {
        /**
         * 操作了某个item
         *
         * @param position
         * @param entry
         */
        void onItemClickChanged(int position, ConditionEntry entry);
    }

    public SimpleWaterfallConditionPopup(@NonNull Context context, List<ConditionEntry> originalData) {
        super(context);
        this.mContext = context;
        this.originalData = originalData;
//        copy();
    }


    /**
     * 拷贝数据
     */
    private void copy() {
        try {

            if (data == null) {
                data = new ArrayList<>();
            }
            data.clear();
            for (int i = 0; i < originalData.size(); i++) {
                ConditionEntry group = (ConditionEntry) originalData.get(i).deepClone();

                Log.d("screen---", "group--" + group);
                data.add(group);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addOnSimpleConditionChangedListener(OnSimpleConditionChangedListener waterFallChangedListener) {
        this.waterFallChangedListener = waterFallChangedListener;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.layout_simple_waterfall_conditon;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mAdapter = new SimpleConditionAdapter(mContext, originalData);
        mAdapter.addOnConditionItemClickListener(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }


}
