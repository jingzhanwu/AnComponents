package com.mti.component.master.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.webkit.WebView;


/**
 * @anthor created by jzw
 * @date 2020/5/18
 * @change
 * @describe describe
 **/
public abstract class BaseActivity extends AppCompatActivity {
    private final static String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initViews(savedInstanceState);
    }

    public abstract int getLayoutId();

    public abstract void initViews(Bundle savedInstanceState);

    /**
     * 设置toolbar 标题
     *
     * @param title
     */
    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    /**
     * 显示toolbar的返回按钮
     */
    public void showToolbarBackView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * 设置webview html数据
     *
     * @param fileName html全名称，****.html
     * @param webView WebView
     */
    public void setMarkdownData(String fileName, WebView webView) {
        if (fileName != null && webView != null) {
//            String content = AssetsUtil.getAssetsFileContent(this, fileName);
//        RichText.from(content).autoFix(true).into(tvRichText);
            webView.loadUrl("file:///android_asset/" + fileName);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
