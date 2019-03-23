package com.demo.aw1802;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.airwatch.app.AWApplication;
import com.airwatch.gateway.ui.GatewaySplashActivity;
import com.airwatch.login.branding.BrandingManager;
import com.airwatch.login.branding.BrandingProvider;
import com.airwatch.login.branding.DefaultBrandingManager;
import com.airwatch.sdk.context.SDKContextManager;
import com.airwatch.sdk.context.awsdkcontext.SDKDataModelImpl;

import org.jetbrains.annotations.NotNull;

import java.security.cert.X509Certificate;

/**
 * Created by ThinkPad on 2017/11/9.]
 * 初始化
 */

public class AirWatchSDKSampleApp extends AWApplication implements BrandingProvider {

    public static String TAG = "TestAirWatchSDK";
    public static String CLEAR_APP_DATA_BROADCAST = "com.sample.airwatchsdk.clearappdata.BROADCAST";
    public static String APP_CONFIG_CHANGE_BROADCAST = "com.sample.airwatchsdk.app.config.change.BROADCAST";
    public static String APP_CONFIG_CHANGE_BROADCAST_EXTRA_CONFIG = "configuration";
    private static Context context = null;
    private BrandingManager brandingManager;

    @Override
    public Intent getMainLauncherIntent() {
        return new Intent(getAppContext(), GatewaySplashActivity.class);
    }

    @Override
    public void onPostCreate() {
        super.onPostCreate();
        context = getApplicationContext();
        brandingManager = new DefaultBrandingManager(
                SDKContextManager.getSDKContext().getSDKConfiguration(),
                new SDKDataModelImpl(getApplicationContext()),
                getApplicationContext(), false);
    }

    @NonNull
    @NotNull
    @Override
    public BrandingManager getBrandingManager() {
        return brandingManager;
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