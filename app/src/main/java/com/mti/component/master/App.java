package com.mti.component.master;

import android.app.Application;

import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.header.FalsifyHeader;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.header.TwoLevelHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.zzhoujay.richtext.RichText;

/**
 * @anthor created by jzw
 * @date 2020/5/21
 * @change
 * @describe describe
 **/
public class App extends Application {

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
            return new MaterialHeader(context);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new BallPulseFooter(context);
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
