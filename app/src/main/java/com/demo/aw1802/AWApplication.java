package com.demo.aw1802;

import android.support.multidex.MultiDexApplication;

import com.airwatch.login.branding.BrandingManager;
import com.airwatch.login.branding.BrandingProvider;
import com.airwatch.login.branding.DefaultBrandingManager;
import com.airwatch.sdk.context.SDKContextManager;
import com.airwatch.sdk.context.awsdkcontext.SDKDataModelImpl;

public abstract class AWApplication extends MultiDexApplication implements BrandingProvider {

    private BrandingManager brandingManager;

    @Override
    public final void onCreate() {
        super.onCreate();
        brandingManager = new DefaultBrandingManager(
                SDKContextManager.getSDKContext().getSDKConfiguration(),
                new SDKDataModelImpl(getApplicationContext()),
                getApplicationContext(), true);
    }

    @Override
    public boolean isInputLogoBrandable() {
        return true;
    }

    @Override
    public BrandingManager getBrandingManager() {
        return brandingManager;
    }
}
