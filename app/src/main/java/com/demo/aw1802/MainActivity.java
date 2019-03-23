package com.demo.aw1802;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.airwatch.gateway.ui.GatewayBaseActivity;
import com.airwatch.ui.widget.AWButton;
import com.airwatch.ui.widget.AWEditText;
import com.airwatch.ui.widget.AWTextView;

public class MainActivity extends GatewayBaseActivity {

    AWTextView textView;
    AWEditText editText;

    AWButton awButton,awBtnWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
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
                Intent intent = new Intent(MainActivity.this,ConsoleActivity.class);
                startActivity(intent);
            }
        });
        awBtnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AWebviewActivity.class);
                startActivity(intent);
            }
        });
    }
}
