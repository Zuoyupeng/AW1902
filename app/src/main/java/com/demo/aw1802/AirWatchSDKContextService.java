package com.demo.aw1802;


import android.os.Build;

import com.airwatch.sdk.SDKBaseContextService;
import com.airwatch.sdk.context.SDKContext;
import com.airwatch.util.Logger;

public class AirWatchSDKContextService extends SDKBaseContextService {

    public AirWatchSDKContextService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * this is to schedule the sdk fetch setting retry
     * it is set to 10 min by default but it can be increased
     * as setting is not that frequent to change
     *
     * @return int time in milliseconds
     */
    @Override
    protected int getSchedulerTime() {
        return  10 * 60 * 1000;
    }

    @Override
    protected String getBuildUserAgent() {
        try {
            return getString(R.string.app_name) + "/" + getApplicationContext().getPackageManager()
                    .getPackageInfo(getPackageName(), 0).versionName
                    + "/Android/" + Build.VERSION.RELEASE;
        } catch (Exception e) {
            Logger.d("unable to fetch user agent",e);
        }
        return "";
    }

    @Override
    protected void requireInitialization() {
    }

    @Override
    protected void requireConfiguration() {
    }

    @Override
    protected void onInitRequired() {
    }

    @Override
    protected void onConfigured() {

    }

    @Override
    protected void onMagCertFetched() {
    }

    @Override
    protected void onInitialized() {
    }

    @Override
    protected void onError(String errorMessage, SDKContext.ErrorCode error) {
    }
}

