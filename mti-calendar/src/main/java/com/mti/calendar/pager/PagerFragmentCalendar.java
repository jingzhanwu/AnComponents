package com.mti.calendar.pager;


import android.support.v7.widget.LinearLayoutManager;

import com.mti.calendar.Article;
import com.mti.calendar.ArticleAdapter;
import com.mti.calendar.R;
import com.mti.calendar.base.fragment.CalendarBaseFragment;
import com.mti.calendar.group.GroupItemDecoration;
import com.mti.calendar.group.GroupRecyclerView;

public class PagerFragmentCalendar extends CalendarBaseFragment {

    private GroupRecyclerView mRecyclerView;


    static PagerFragmentCalendar newInstance() {
        return new PagerFragmentCalendar();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pager;
    }

    @Override
    protected void initView() {
        mRecyclerView = mRootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new GroupItemDecoration<String, Article>());
        mRecyclerView.setAdapter(new ArticleAdapter(mContext));
        mRecyclerView.notifyDataSetChanged();
    }

    @Override
    protected void initData() {

    }

    boolean isScrollTop() {
        return mRecyclerView != null && mRecyclerView.computeVerticalScrollOffset() == 0;
    }
}
