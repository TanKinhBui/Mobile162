package com.example.tankinhbui.mobile_convert_money;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static android.view.View.*;

public class MainActivity extends AppCompatActivity {

    Button btnShow, btnConvert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnShow = (Button) findViewById(R.id.buttonShow);
        btnShow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent screen2 = new Intent(MainActivity.this, ShowActivity.class);
                startActivity(screen2);
            }
        });

        btnConvert = (Button) findViewById(R.id.buttonConvert);
        btnConvert.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent screen3 = new Intent(MainActivity.this, ConvertActivity.class);
                startActivity(screen3);
            }
        });
    }
}
