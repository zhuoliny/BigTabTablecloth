package com.example.zhuoliny.bigtabtablecloth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
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
                Intent intentS = new Intent(getApplicationContext(), SquareClick.class);
                startActivity(intentS);
            }
        });

        circle = (Button) findViewById(R.id.CircleClick);
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentC = new Intent(getApplicationContext(), CircleClick.class);
                startActivity(intentC);
            }
        });
    }
}
