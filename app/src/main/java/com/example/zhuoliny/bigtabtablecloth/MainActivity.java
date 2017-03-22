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
    String gender;
    private UserInfo user;
    private writeCSV info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        userName = (EditText) findViewById(R.id.userName);
        moreInfo = (EditText) findViewById(R.id.moreInfo);
        age = (EditText) findViewById(R.id.userAge);

        gender = ""; // unknown
        female = (Button) findViewById(R.id.Female);
        male = (Button) findViewById(R.id.Male);
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setBackgroundColor(Color.RED);
                female.setTextColor(Color.WHITE);
                male.setBackgroundColor(Color.LTGRAY);
                male.setTextColor(Color.BLACK);
                gender = "F";
            }
        });
        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                male.setBackgroundColor(Color.BLUE);
                male.setTextColor(Color.WHITE);
                female.setBackgroundColor(Color.LTGRAY);
                female.setTextColor(Color.BLACK);
                gender = "M";
            }
        });

        save = (Button) findViewById(R.id.SaveInfo);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = new UserInfo(userName.getText().toString(), gender, Integer.parseInt(age.getText().toString()), moreInfo.getText().toString());
                Intent toConsole = new Intent(getApplicationContext(), Console.class);
                toConsole.putExtra("aUser", user);
                startActivity(toConsole);
            }
        });
    }
}
