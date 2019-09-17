package com.demo.aw1802;

import android.os.Bundle;
import android.widget.ListView;

import com.airwatch.login.SDKBaseActivity;
import com.airwatch.sdk.AirWatchSDKException;
import com.airwatch.sdk.SDKManager;
import com.airwatch.sdk.SecureAppInfo;
import com.airwatch.sdk.profile.PasscodePolicy;
import com.airwatch.sdk.profile.RestrictionPolicy;
import com.demo.aw1802.watermark.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsoleActivity extends BaseActivity {

    SDKManager awSDKManager = null;
    boolean serviceError = false;
    RestrictionPolicy restrictionPolicy = null;
    PasscodePolicy passcodePolicy = null;
    SecureAppInfo appInfo = null;

    private static final String API_VERSION = "API Version";
    private static final String DEVICE_UID = "Device UID";
    private static final String SERVER_NAME = "Server Name";
    private static final String GROUP_ID = "Group ID";
    private static final String AUTHENTICATION_TYPE = "Authentication Type";
    private static final String AUTHENTICATE_USER = "Authenticate User";
    private static final String PASSWORD_REQUIRED = "Is Passcode Required";
    private static final String PASSWORD_COMPLEXITY = "Passcode Complexity";
    private static final String MIN_PASSCODE_LENGTH = "Min Passcode Length";
    private static final String MIN_COMPLEX_CHAR_LENGTH = "Min Complex Char Length";
    private static final String MAXIMUM_PASSCODE_AGE = "Maximum Passcode Age";
    private static final String PASSCODE_HISTORY = "Passcode History";
    private static final String IS_COMPLIANT = "Is Compliant";
    private static final String IS_COMPROMISED = "Is Compromised";
    private static final String IS_ENTERPRISE = "Is Enterprise";
    private static final String BLUETOOTH_ALLOWED = "Bluetooth Allowed";
    private static final String CAMERA_ALLOWED = "Camera Allowed";
    private static final String OFFLINE_MODE_ALLOWED = "Offline Mode Allowed";
    private static final String COPY_AND_CUT = "Prevent Copy and Cut";
    private static final String WIFI = "Wifi SSID Allowed";
    private static final String APPLICATION_PROFILE_ID = "Application Profile ID";
    private static final String APPLICATION_PROFILE_NAME = "Application Profile Name";
    private static final String SSO_SESSION_VALID = "Is SSO Session Valid";
    private static final String SSO_ENABLED = "Is SSO Enabled";
    private static final String SSO_GRACE_PERIOD = "SSO Grace Period";
    private static final String ANCHOR_TYPE = "Anchor Type";
    private static final String CONSOLE_VERSION = "Console Version";
    private static final String PROFILE_GROUPS = "Profile Groups";
    private static final String LAST_CHECKIN_TIME = "Last Checkin Time";
    private static final String ALLOWED_WIFI_SSID = "Allowed WiFi SSID";
    private static final String SSO_STATUS = "SSO Status";
    private static final String ENROLLMENT_USERNAME = "Enrollment Username";
    ListView lvApi;

    ApiAdapter mainList = null;
    Map<String, String> map = new HashMap<>();
    String[] title_array = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        lvApi = findViewById(R.id.lv_api);
        title_array = getResources().getStringArray(R.array.api_list_items);
        mainList = new ApiAdapter(this, R.array.api_list_items, map);
        lvApi.setAdapter(mainList);
    }

    public void setup() {
        if (awSDKManager == null) {
            try {
                awSDKManager = SDKManager.init(getApplicationContext());
                serviceError = false;
            } catch (AirWatchSDKException e) {
                serviceError = true;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SDKManager.isServiceConnected()) {
            setup();
            for (String s : title_array) {
                getResponse(s);
            }
        }
    }

    public void getResponse(final String title) {
        new Thread(new Runnable() {
            public void run() {
                String response = null;
                try {
                    if (serviceError) {
                        awSDKManager = SDKManager.init(getApplicationContext());
                    }
                    StringBuilder builder;
                    switch (title) {
                        //region API Version
                        case API_VERSION:
                            response = "" + awSDKManager.getAPIVersion();
                            break;
                        //region Device UID
                        case DEVICE_UID:
                            response = "" + awSDKManager.getDeviceUid();
                            break;
                        //region Enrollment Username
                        case ENROLLMENT_USERNAME:
                            response = awSDKManager.getEnrollmentUsername();
                            break;
                        //region Server Name
                        case SERVER_NAME:
                            response = "" + awSDKManager.getServerName();
                            break;
                        //endregion
                        //region Group ID
                        case GROUP_ID:
                            response = "" + awSDKManager.getGroupId();
                            break;
                        //endregion
                        //region Authentication Type
                        case AUTHENTICATION_TYPE:
                            response = "" + awSDKManager.getAuthenticationType();
                            break;
                        //endregion
                        //region Authenticate User
                        case AUTHENTICATE_USER:
                            appInfo = awSDKManager.getSecureAppInfo();
                            response = "" + awSDKManager.authenticateUser(appInfo.getEnrollmentUsername(), appInfo.getEnrollmentPassword());
                            break;
                        //endregion
                        //region Is Passcode Required
                        case PASSWORD_REQUIRED:
                            passcodePolicy = awSDKManager.getPasscodePolicy();
                            response = "" + passcodePolicy.isPasscodeRequired();
                            break;
                        //endregion
                        //region Passcode Complexity
                        case PASSWORD_COMPLEXITY:
                            passcodePolicy = awSDKManager.getPasscodePolicy();
                            int complexity = passcodePolicy.getPassscodeComplexity();
                            switch (complexity) {
                                case 1:
                                    response = "Numeric";
                                    break;
                                case 2:
                                    response = "Alpha numeric";
                                    break;
                                default:
                                    response = "Unknown";
                                    break;
                            }
                            break;
                        //endregion
                        //region Min Passcode Length
                        case MIN_PASSCODE_LENGTH:
                            passcodePolicy = awSDKManager.getPasscodePolicy();
                            response = "" + passcodePolicy.getMinPasscodeLength();
                            break;
                        //endregion
                        //region Min Complex Char Length
                        case MIN_COMPLEX_CHAR_LENGTH:
                            passcodePolicy = awSDKManager.getPasscodePolicy();
                            response = "" + passcodePolicy.getMinComplexChars();
                            break;
                        //endregion
                        //region Maximum Passcode Age
                        case MAXIMUM_PASSCODE_AGE:
                            passcodePolicy = awSDKManager.getPasscodePolicy();
                            response = "" + passcodePolicy.getMaxPasscodeAge() + " day(s)";
                            break;
                        //endregion
                        //region Passcode History
                        case PASSCODE_HISTORY:
                            passcodePolicy = awSDKManager.getPasscodePolicy();
                            response = "" + passcodePolicy.getPasscodeHistory();
                            break;
                        //endregion
                        //region Compliant Status
                        case IS_COMPLIANT:
                            response = "" + awSDKManager.isCompliant();
                            break;
                        //endregion
                        //region Compromise Status
                        case IS_COMPROMISED:
                            response = "" + awSDKManager.isCompromised();
                            break;
                        //endregion
                        //region Enterprise Status
                        case IS_ENTERPRISE:
                            response = "" + awSDKManager.isEnterprise();
                            break;
                        //endregion
                        //region Bluetooth
                        case BLUETOOTH_ALLOWED:
                            restrictionPolicy = awSDKManager.getRestrictionPolicy();
                            response = "" + restrictionPolicy.allowBluetooth();
                            break;
                        //endregion
                        //region Camera
                        case CAMERA_ALLOWED:
                            restrictionPolicy = awSDKManager.getRestrictionPolicy();
                            response = "" + restrictionPolicy.allowCamera();
                            break;
                        //endregion
                        //region Offline Mode
                        case OFFLINE_MODE_ALLOWED:
                            restrictionPolicy = awSDKManager.getRestrictionPolicy();
                            response = "" + restrictionPolicy.allowOfflineMode();
                            break;
                        //endregion
                        //region Copy and Cut
                        case COPY_AND_CUT:
                            restrictionPolicy = awSDKManager.getRestrictionPolicy();
                            response = "" + restrictionPolicy.preventCopyAndCutActions();
                            break;
                        //endregion
                        //region WiFi SSID
                        case WIFI:
                            response = "" + awSDKManager.isAllowedWiFiSSID();
                            break;
                        //endregion
                        //region Application Profile ID
                        case APPLICATION_PROFILE_ID:
                            response = "" + awSDKManager.getApplicationProfile().getProfileId();
                            break;
                        //endregion
                        //region Application Profile Name
                        case APPLICATION_PROFILE_NAME:
                            response = "" + awSDKManager.getApplicationProfile().getName();
                            break;
                        //region Is SSO Session Valid
                        case SSO_SESSION_VALID:
                            response = "" + awSDKManager.isSSOSessionValid();
                            break;
                        //endregion
                        //region Is SSO Enabled
                        case SSO_ENABLED:
                            response = "" + awSDKManager.isSSOEnabled();
                            break;
                        //endregion
                        //region SSO Grace Period
                        case SSO_GRACE_PERIOD:
                            response = "" + awSDKManager.getSSOGracePeriod() + " minute(s)";
                            break;
                        //region Anchor Type
                        case ANCHOR_TYPE:
                            int anchorType = awSDKManager.getAnchorAppStatus().getAnchorType();
                            switch (anchorType) {
                                case 0:
                                    response = "Agent";
                                    break;
                                case 1:
                                    response = "Workspace";
                                    break;
                                default:
                                    response = "Unknown";
                                    break;
                            }
                            break;
                        //endregion
                        //region Console Version
                        case CONSOLE_VERSION:
                            response = "" + awSDKManager.getConsoleVersion();
                            break;
                        //region Profile Groups
                        case PROFILE_GROUPS:
                            List<String> groups = awSDKManager.getAllProfileGroups();
                            if (groups != null) {
                                builder = new StringBuilder();
                                for (String s : groups) {
                                    builder.append(s + "\n");
                                }
                                response = builder.toString();
                            } else {
                                response = "Profile Groups are empty";
                            }
                            break;
                        //region Last Checkin Time
                        case LAST_CHECKIN_TIME:
                            String strDateFormat = "EEE, dd MMM yyyy HH:mm:ss z";
                            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
                            response = sdf.format(awSDKManager.getDeviceLastCheckinTime());
                            break;
                        //region Allowed WiFi SSID
                        case ALLOWED_WIFI_SSID:
                            response = "" + awSDKManager.isAllowedWiFiSSID();
                            break;
                        //region SSO Status
                        case SSO_STATUS:
                            response = "" + awSDKManager.getSSOStatus();
                            break;
                    }
                    serviceError = false;
                } catch (AirWatchSDKException e) {
                    response = "not available";
                    serviceError = true;
                } finally {
                    //region Update result
                    if (response != null) {
                        map.put(title, response);
                        updateUI();
                    }
                }
            }
        }).start();
    }

    public void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (lvApi != null && mainList != null) {
                    mainList.notifyDataSetChanged();
                }
            }
        });
    }
}
