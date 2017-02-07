package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    Button square;
    Button circle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        square = (Button) findViewById(R.id.SquareClick);
        square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentS = new Intent(getApplicationContext(), Sequence.class);
                startActivity(intentS);
            }
        });

        circle = (Button) findViewById(R.id.CircleClick);
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentC = new Intent(getApplicationContext(), Sequence.class);
                startActivity(intentC);
            }
        });
    }
}
