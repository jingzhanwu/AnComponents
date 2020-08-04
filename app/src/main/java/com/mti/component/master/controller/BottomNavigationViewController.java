package com.mti.component.master.controller;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.mti.component.master.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @anthor created by jzw
 * @date 2020/3/23
 * @change
 * @describe bottomNavigationView 控制器
 **/
public class BottomNavigationViewController {

    private static class Holder {
        private static final BottomNavigationViewController INSTANCE = new BottomNavigationViewController();
    }

    private BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;
    private List<Integer> menuItemIdList;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentList;
    private AppCompatActivity activity;
    private OnPagerChangedListener onPagerChangedListener;


    private BottomNavigationViewController() {
    }

    public static BottomNavigationViewController getInstance() {
        return Holder.INSTANCE;
    }

    private void init() {
        //设置bottomNavigationView的监听器
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            menuItem = item;
            int itemId = item.getItemId();
            for (int i = 0; i < menuItemIdList.size(); i++) {
                if (itemId == menuItemIdList.get(i)) {
                    viewPager.setCurrentItem(i);
                    return true;
                }
            }
            return false;
        });

        //设置viewPage 事件监听
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
                if (onPagerChangedListener != null) {
                    onPagerChangedListener.onPageSelected(position, menuItem.getTitle().toString());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPagerAdapter.setFragments(fragmentList);
        viewPager.setAdapter(viewPagerAdapter);

        //旧版api去除动画
//        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        viewPager.setCurrentItem(0);
    }

    public interface OnPagerChangedListener {
        void onPageSelected(int position, String menuTitle);
    }

    /**
     * builder 模式来初始化BottomNavigationViewController
     */
    public static class Builder {
        private final BottomNavigationViewController INSTANCE;

        public Builder() {
            INSTANCE = Holder.INSTANCE;
            INSTANCE.fragmentList = new ArrayList<>();
            INSTANCE.menuItemIdList = new ArrayList<>();
        }

        public Builder setActivity(AppCompatActivity activity) {
            INSTANCE.activity = activity;
            INSTANCE.viewPagerAdapter = new ViewPagerAdapter(activity.getSupportFragmentManager());
            return this;
        }

        public Builder setBottomNavigationView(BottomNavigationView navigationView) {
            INSTANCE.bottomNavigationView = navigationView;
            return this;
        }

        public Builder setViewPager(ViewPager viewPager) {
            INSTANCE.viewPager = viewPager;
            return this;
        }

        public Builder addFragmentAndMenuItem(Fragment fragment, int menuItemId) {
            INSTANCE.fragmentList.add(fragment);
            INSTANCE.menuItemIdList.add(Integer.valueOf(menuItemId));
            return this;
        }

        public Builder setOnPagerChangedListener(OnPagerChangedListener listener) {
            INSTANCE.onPagerChangedListener = listener;
            return this;
        }

        public BottomNavigationViewController build() {
            INSTANCE.init();
            return INSTANCE;
        }
    }
}
