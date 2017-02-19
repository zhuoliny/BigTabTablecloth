package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    Button save, console;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        console = (Button) findViewById(R.id.Console);
        console.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toConsole = new Intent(getApplicationContext(), Console.class);
                startActivity(toConsole);
            }
        });
    }
}
