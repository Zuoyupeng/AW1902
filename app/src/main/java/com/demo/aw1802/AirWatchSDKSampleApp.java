package com.demo.aw1802;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;

import com.airwatch.app.AWApplication;
import com.airwatch.gateway.ui.GatewaySplashActivity;
import com.demo.aw1802.watermark.DensityUtil;
import com.demo.aw1802.watermark.WaterMarkInfo;
import com.demo.aw1802.watermark.WaterMarkManager;

import java.security.cert.X509Certificate;

/**
 * Created by ThinkPad on 2017/11/9.]
 * 初始化
 */

public class AirWatchSDKSampleApp extends AWApplication {

    public static String TAG = "TestAirWatchSDK";
    public static String CLEAR_APP_DATA_BROADCAST = "com.sample.airwatchsdk.clearappdata.BROADCAST";
    public static String APP_CONFIG_CHANGE_BROADCAST = "com.sample.airwatchsdk.app.config.change.BROADCAST";
    public static String APP_CONFIG_CHANGE_BROADCAST_EXTRA_CONFIG = "configuration";
    private static Context context = null;

    @Override
    public Intent getMainLauncherIntent() {
        return new Intent(getAppContext(), GatewaySplashActivity.class);
    }

    @Override
    public void onPostCreate() {
        super.onPostCreate();
        context = getApplicationContext();
        setAppWaterMark();
    }

    /**
     * 水印全局设置
     */
    public void setAppWaterMark() {
        WaterMarkManager.setInfo(
                WaterMarkInfo.create()
                        .setDegrees(-45)
                        .setTextSize(DensityUtil.dp2px(getApplicationContext(),20))
                        .setTextColor(Color.parseColor("#33000000"))//"#220000FF"
                        .setTextBold(true)
                        .setDx(DensityUtil.dp2px(getApplicationContext(),60))
                        .setDy(DensityUtil.dp2px(getApplicationContext(),60))
                        .setAlign(Paint.Align.CENTER)
                        .generate());
    }


    public static Context getAppContext() {
        return context;
    }

    @Override
    public Intent getMainActivityIntent() {
        return new Intent(getAppContext(), MainActivity.class);
    }

    @Override
    public void onSSLPinningValidationFailure(String host, @Nullable X509Certificate
            x509Certificate) {
    }

    @Override
    public void onSSLPinningRequestFailure(String host, @Nullable X509Certificate x509Certificate) {

    }

    @Override
    public boolean isInputLogoBrandable() {
        return true;
    }

    @Override
    public boolean getMagCertificateEnable() {
        return true;
    }


}