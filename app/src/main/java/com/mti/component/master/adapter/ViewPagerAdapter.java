package com.mti.component.master.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor created by jzw
 * @date 2020/3/23
 * @change
 * @describe viewPage 适配器
 **/
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private List<String> title;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }

    public void setTitle(List<String> title) {
        this.title = title == null ? new ArrayList<>() : title;
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return this.title == null ? "" : this.title.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments != null ? fragments.size() : 0;
    }
}
