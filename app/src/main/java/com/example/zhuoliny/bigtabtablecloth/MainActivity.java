package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    Button sequentially;
    Button randomly;
    int sequentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sequentially = (Button) findViewById(R.id.sequentially);
        sequentially.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentA = new Intent(getApplicationContext(), TypeChoices.class);
                sequentType = 0;
                intentA.putExtra("type", sequentType);
                startActivity(intentA);
            }
        });

        randomly = (Button) findViewById(R.id.randomly);
        randomly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentB = new Intent(getApplicationContext(), TypeChoices.class);
                sequentType = 1;
                intentB.putExtra("type", sequentType);
                startActivity(intentB);
            }
        });
    }
}
