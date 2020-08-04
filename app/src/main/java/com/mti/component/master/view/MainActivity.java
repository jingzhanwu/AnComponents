package com.mti.component.master.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.mti.component.master.R;
import com.mti.component.master.base.BaseActivity;
import com.mti.component.master.controller.BottomNavigationViewController;
import com.mti.component.master.view.main.ComponentFragment;
import com.mti.component.master.view.main.DevelopmentFragment;
import com.mti.component.master.view.main.FeaturedFragment;
import com.mti.component.master.view.main.TemplateFragment;
import com.zzhoujay.richtext.RichText;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        requestPermission();
        getSupportActionBar().setTitle("组件");
        initBottomTabView();
    }

    private void initBottomTabView() {
        BottomNavigationView navigationView = findViewById(R.id.bottomNavigationView);
        viewPager = findViewById(R.id.viewPage);
        viewPager.setOffscreenPageLimit(5);
        new BottomNavigationViewController.Builder()
                .setActivity(this)
                .setBottomNavigationView(navigationView)
                .setViewPager(viewPager)
                .addFragmentAndMenuItem(new ComponentFragment(), R.id.menu_component)
                .addFragmentAndMenuItem(new TemplateFragment(), R.id.menu_template)
                .addFragmentAndMenuItem(new FeaturedFragment(), R.id.menu_featured)
                .addFragmentAndMenuItem(new DevelopmentFragment(), R.id.menu_development)
                .setOnPagerChangedListener((position, menuTitle) -> {
                    //页面tab变化
                    getSupportActionBar().setTitle(menuTitle);
                })
                .build();

    }

    private void initRichText() {
        RichText.initCacheDir(getApplicationContext());
    }

    /**
     * 权限申请
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permission = {
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.VIBRATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,
            };
            int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            int result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int result3 = ContextCompat.checkSelfPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW);
            if (result1 != PackageManager.PERMISSION_GRANTED
                    || result2 != PackageManager.PERMISSION_GRANTED
                    || result3 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permission, 100);
            } else {
                initRichText();
            }
        } else {
            initRichText();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        initRichText();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
