package com.demo.aw1802;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airwatch.gateway.clients.AWWebView;
import com.airwatch.gateway.clients.AWWebViewClient;
import com.airwatch.gateway.ui.GatewayBaseActivity;
import com.airwatch.sdk.AirWatchSDKException;
import com.airwatch.sdk.SDKManager;

public class AWebviewActivity extends GatewayBaseActivity {

    SDKManager awSDKManager = null;
    boolean serviceError = false;
    public static final String TAG = "AWDemo";
    private Button mGoBtn;
    private AWWebView webView;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awebview);
        mGoBtn = (Button) findViewById(R.id.go_btn);
        mGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(input.getText()))
                    loadContent();
            }
        });
        webView = (AWWebView) findViewById(R.id.aw_web);
        input = (EditText) findViewById(R.id.input_password);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new AWWebViewClient(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SDKManager.isServiceConnected()) {
            setup();
            loadDefultContent();
        }
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

    private void loadContent() {
        String url = input.getText().toString();
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(AWebviewActivity.this, "Url please", Toast.LENGTH_LONG).show();
            return;
        }
        if (!(url.startsWith("http://") || url.startsWith("https://"))) {
            url = "http://" + url;
        }
        webView.loadUrl(url);
    }

    private void loadDefultContent() {
        String url = null;
        try {
            url = "https://" + awSDKManager.getServerName() + "/Catalog/ViewCatalog/" +
                    awSDKManager.getDeviceUid() + "/Android";
        } catch (AirWatchSDKException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(AWebviewActivity.this, "Url please", Toast.LENGTH_LONG).show();
            return;
        }
        if (!(url.startsWith("http://") || url.startsWith("https://"))) {
            url = "http://" + url;
        }
        webView.loadUrl(url);
    }
}
