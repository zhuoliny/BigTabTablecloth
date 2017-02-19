package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;

public class Console extends Activity {
    private Attribute attr;
    private int[] sequence;
    private int size, color, lightLmt, timeLmt;

    Button sequential, random, customize;
    Button large, medium, small;
    Button green, red, blue;
    EditText lightLimit, timeLimit;
    Dialog popupSeq;
    EditText seqIndex;
    Button popupSave;
    String seqInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        lightLmt = -1;
        lightLimit = (EditText) findViewById(R.id.lightLimit);
        String temp1 = lightLimit.getText().toString();
        if (onlyDigits(temp1)) {
            lightLmt = Integer.parseInt(temp1);
            lightLimit.setHintTextColor(Color.LTGRAY);
            lightLimit.setHint("Just enter the integer you want");
        }else{
            if (!temp1.isEmpty()) {
                lightLimit.setHintTextColor(Color.RED);
                lightLimit.setHint("Please use the correct format");
                lightLimit.setText("");
            }
        }

        timeLmt = -1;
        timeLimit = (EditText) findViewById(R.id.timeLimit);
        String temp2 = timeLimit.getText().toString();
        if (onlyDigits(temp1)) {
            timeLmt = Integer.parseInt(temp2);
            timeLimit.setHintTextColor(Color.LTGRAY);
            timeLimit.setHint("Just enter the integer you want");
        }else{
            if (!temp2.isEmpty()) {
                timeLimit.setHintTextColor(Color.RED);
                timeLimit.setHint("Please use the correct format");
                timeLimit.setText("");
            }
        }

        sequential = (Button) findViewById(R.id.sequential);
        sequential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sequence = new int[9];
                for (int i=0; i<9; i++) {
                    sequence[i] = i;
                }
            }
        });

        random = (Button) findViewById(R.id.random);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lightLmt != -1) {
                    sequence = new int[lightLmt];
                    for (int i = 0; i < sequence.length; i++) {
                        Random r = new Random();
                        int position = r.nextInt(9);
                        sequence[i] = position;
                    }
                }
            }
        });

        popupSeq = new Dialog(this);
        popupSeq.setContentView(R.layout.seq_enter_popup);
        customize = (Button) findViewById((R.id.Customize));
        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seqIndex = (EditText) popupSeq.findViewById(R.id.seqIndex);
                popupSave = (Button) popupSeq.findViewById(R.id.saveSeq);
                popupSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        seqInt = seqIndex.getText().toString();
                        popupSeq.dismiss();
                    }
                });
               popupSeq.show();
            }
        });
    }

    public boolean onlyDigits(String s) {
        String regex = "[0-9]+";
        return s.matches(regex);
    }
}
