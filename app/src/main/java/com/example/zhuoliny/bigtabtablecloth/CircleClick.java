package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class CircleClick extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_click);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }
}
