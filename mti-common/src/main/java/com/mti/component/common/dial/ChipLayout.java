package com.mti.component.common.dial;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mti.component.common.R;

import java.util.List;

/**
 * 筹码布局
 * Created by xue on 2017/1/21.
 */

public class ChipLayout extends BaseUniformViewGroup {

    public ChipLayout(Context context) {
        this(context, null);
    }

    public ChipLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void addChipList(List<String> chipList, final OnSingleSelectListener listener) {

        if (chipList==null||chipList.size()==0) {
            return;
        }


        removeAllViews();

        setRankNum(3);
        setVerticalDpVal(8);
        setHorizontalDpVal(10);
        setItemContentHeight(40);

        LinearLayout itemView = null;
        TextView textView = null;
        for (int i = 0; i < chipList.size(); i++) {

            itemView = (LinearLayout) View.inflate(getContext(), R.layout.item_keyboard_layout, null);
            itemView.setTag(i);
            textView = (TextView) itemView.getChildAt(0);
            if (i == 0) {
                textView.setSelected(true);
            }
            textView.setText(chipList.get(i));
            itemView.setOnClickListener(v -> {
                int position = (int) v.getTag();
                if (listener != null) {
                    performChangeStatus(position);
                    listener.onSingleSelect(position);
                }

            });
            addView(itemView);
        }


    }

    private void performChangeStatus(int position) {

    }


}
