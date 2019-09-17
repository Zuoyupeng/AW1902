package com.demo.aw1802.watermark;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.ViewGroup;

import com.airwatch.gateway.ui.GatewayBaseActivity;

public class BaseActivity extends GatewayBaseActivity {

    WaterMarkView markView;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        markView = WaterMarkManager.getView(this);
        ((ViewGroup) findViewById(android.R.id.content)).addView(markView);
    }

    @Override
    protected void onDestroy() {
        if (markView != null) {
            markView.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
}
