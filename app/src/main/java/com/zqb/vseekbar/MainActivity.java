package com.zqb.vseekbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VSeekBar vSeekBar = findViewById(R.id.v_seek_bar);
        vSeekBar.setOnSeekBarChangeListener(new VSeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(float progress) {
                LogUtils.w(progress + "_");
            }

            @Override
            public void onStartTrackingTouch(float progress) {
                LogUtils.w(progress + "_ _");
            }

            @Override
            public void onStopTrackingTouch(float progress) {
                LogUtils.w(progress + "_ _ _");
            }
        });
        vSeekBar.setProgress(20);
    }
}
