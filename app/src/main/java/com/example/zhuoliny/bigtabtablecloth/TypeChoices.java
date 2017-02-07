package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TypeChoices extends Activity {
    Button square, circle;
    int sequenceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_choices);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent receiver = getIntent();
        sequenceType = (int) receiver.getSerializableExtra("type");

        square = (Button) findViewById(R.id.SquareClick);
        square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSq = new Intent(getApplicationContext(), SquareClick.class);
                intentSq.putExtra("type", sequenceType);
                startActivity(intentSq);
            }
        });

        circle = (Button) findViewById(R.id.CircleClick);
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCr = new Intent(getApplicationContext(), CircleClick.class);
                intentCr.putExtra("type", sequenceType);
                startActivity(intentCr);
            }
        });
    }
}
