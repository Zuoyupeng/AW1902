/*
 * AirWatch Android Software Development Kit
 * Copyright (c) 2013 AirWatch. All rights reserved.
 *
 * Unless required by applicable law, this Software Development Kit and sample
 * code is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF
 * ANY KIND, either express or implied. AirWatch expressly disclaims any and all
 * liability resulting from User's use of this Software Development Kit. Any
 * modification made to the SDK must have written approval by AirWatch.
 */

package com.demo.aw1802;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.airwatch.bizlib.model.CertificateDefinition;
import com.airwatch.log.AWTags;
import com.airwatch.sdk.AirWatchSDKBaseIntentService;
import com.airwatch.sdk.AirWatchSDKConstants;
import com.airwatch.sdk.AirWatchSDKException;
import com.airwatch.sdk.AirWatchSDKServiceIntentHelper;
import com.airwatch.sdk.SDKManager;
import com.airwatch.sdk.p2p.P2PContext;
import com.airwatch.sdk.profile.AnchorAppStatus;
import com.airwatch.sdk.profile.ApplicationProfile;
import com.airwatch.sdk.shareddevice.ClearReasonCode;
import com.airwatch.task.DefaultTaskQueue;
import com.airwatch.util.Logger;

import java.util.List;

public class AirWatchSDKIntentService extends AirWatchSDKBaseIntentService {

    public static final String ENROLLMENT_FINISH = "enrollment_finish";
    public static final String ENROLLMENT_RESULT = "enrollment_result";
    private static final String TAG = AirWatchSDKIntentService.class.getSimpleName();

    @Override
    protected void onApplicationProfileReceived(Context context, String profileId, ApplicationProfile awAppProfile) {
        if (profileId != null)
            CertificateStore.addProfileId(profileId);
        if (awAppProfile != null) {
            List<CertificateDefinition> certs = awAppProfile.getCertificates();
            if (certs != null && certs.size() > 0)
                CertificateStore.addCert(certs.get(0));
        }
    }

    @Override
    protected void onClearAppDataCommandReceived(Context context, ClearReasonCode reasonCode) {
        Intent clearAppDataIntent = new Intent(AirWatchSDKSampleApp.CLEAR_APP_DATA_BROADCAST);
        LocalBroadcastManager.getInstance(context).sendBroadcast(clearAppDataIntent);
        // clear sdk settings
        AirWatchSDKServiceIntentHelper.clearUnifiedPinAndSDKData(context);
        DefaultTaskQueue.postDelayed(() -> android.os.Process.killProcess(android.os.Process.myPid()), 3000);

    }

    @Override
    protected void onAnchorAppStatusReceived(Context ctx, AnchorAppStatus appStatus) {
        Logger.d(AWTags.CHANNEL_TOKEN_PBE_TAG, "anchor status from intentService received : " + appStatus.getWorkspaceExitMode());
        if (appStatus.getWorkspaceExitMode() == 0
                && ((P2PContext) ctx).shouldRegisterForSSO()) {
            AirWatchSDKServiceIntentHelper.lockSSOSession(ctx);
        }
    }

    @Override
    protected void onAnchorAppUpgrade(Context ctx, boolean isUpgrade) {
        Log.d(AirWatchSDKSampleApp.TAG, "Received AnchorAppUpgrade broadcast. AnchorApp is " + (isUpgrade ? "upgrading" : "removed"));
    }

    @Override
    protected void onApplicationConfigurationChange(Bundle applicationConfiguration) {
        Log.d(AirWatchSDKSampleApp.TAG, "received application configuration change " + applicationConfiguration);
        LocalBroadcastManager.getInstance(this).sendBroadcast(
                new Intent(AirWatchSDKSampleApp.APP_CONFIG_CHANGE_BROADCAST)
                        .putExtra(AirWatchSDKSampleApp.APP_CONFIG_CHANGE_BROADCAST_EXTRA_CONFIG, applicationConfiguration));
    }

    @Override
    protected void handleAutoEnrollmentStatus(int status) {
        if (status == AirWatchSDKConstants.AUTO_ENROLLMENT_STATUS_COMPLETE) {
            SDKManager sdkManager;
            boolean isSuccess = false;
            try {
                sdkManager = SDKManager.init(getApplicationContext());
                isSuccess = sdkManager.isEnrolled();
            } catch (AirWatchSDKException e) {
                Logger.e(TAG, "Error in enrollment ", e);
            }
            Intent intent = new Intent(ENROLLMENT_FINISH);
            intent.putExtra(ENROLLMENT_RESULT, isSuccess);
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

            try {
                if (isSuccess) {
                    Thread.sleep(3000);
                    final Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.airwatch.androidagent");
                    startActivity(launchIntent);
                }
            } catch (Exception e) {
                Logger.e(TAG, "Exception in launching agent", e);
            }
        }
    }
}
