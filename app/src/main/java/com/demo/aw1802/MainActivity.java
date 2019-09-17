package com.demo.aw1802;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;

import com.airwatch.gateway.ui.GatewayBaseActivity;
import com.airwatch.sdk.AirWatchSDKException;
import com.airwatch.sdk.SDKManager;
import com.airwatch.ui.widget.AWButton;
import com.airwatch.ui.widget.AWEditText;
import com.airwatch.ui.widget.AWTextView;
import com.demo.aw1802.watermark.BaseActivity;
import com.demo.aw1802.watermark.WaterMarkManager;
import com.demo.aw1802.watermark.WaterMarkView;

public class MainActivity extends BaseActivity {

    AWTextView textView;
    AWEditText editText;

    AWButton awButton, awBtnWeb;

    SDKManager awSDKManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView() {
        if (awSDKManager == null) {
            try {
                awSDKManager = SDKManager.init(getApplicationContext());
                WaterMarkManager.setText(awSDKManager.getServerName(),
                        awSDKManager.getSecureAppInfo().getEnrollmentUsername());
            } catch (AirWatchSDKException e) {
            }
        }
        textView = findViewById(R.id.text_view);
        editText = findViewById(R.id.et);
        awButton = findViewById(R.id.btn_console);
        awBtnWeb = findViewById(R.id.btn_web);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                textView.setText(editText.getEditableText().toString());
            }
        });
        awButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConsoleActivity.class);
                startActivity(intent);
            }
        });
        awBtnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AWebviewActivity.class);
                startActivity(intent);
            }
        });
    }
}
