package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    Button save, console, female, male;
    EditText userName, moreInfo, age;
    int gender;
    private UserInfo user;

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

        userName = (EditText) findViewById(R.id.userName);
        moreInfo = (EditText) findViewById(R.id.moreInfo);
        age = (EditText) findViewById(R.id.userAge);

        gender = -1; // unknown
        female = (Button) findViewById(R.id.Female);
        male = (Button) findViewById(R.id.Male);
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setBackgroundColor(Color.RED);
                female.setTextColor(Color.WHITE);
                male.setBackgroundColor(Color.LTGRAY);
                male.setTextColor(Color.BLACK);
                gender = 0;
            }
        });
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setBackgroundColor(Color.BLUE);
                male.setTextColor(Color.WHITE);
                female.setBackgroundColor(Color.LTGRAY);
                female.setTextColor(Color.BLACK);
                gender = 1;
            }
        });

        save = (Button) findViewById(R.id.SaveInfo);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new UserInfo(userName.getText().toString(), Integer.parseInt(age.getText().toString()), gender, moreInfo.getText().toString());
            }
        });
    }
}
