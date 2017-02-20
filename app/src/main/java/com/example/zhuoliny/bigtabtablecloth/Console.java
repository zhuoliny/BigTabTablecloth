package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.ArrayList;
import java.util.Random;

public class Console extends Activity {
    private Attribute attr;
    private ArrayList<Integer> sequence;
    private int size, color, lightLmt, timeLmt;

    Button sequential, random, customize;
    Button large, medium, small;
    Button green, red, blue;
    EditText lightLimit, timeLimit;
    Dialog popupSeq;
    EditText seqIndex;
    Button popupSave, consoleSave;
    String seqInt;
    String checkLightLmt, checkTimeLmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        lightLmt = -1;
        lightLimit = (EditText) findViewById(R.id.lightLimit);
        timeLmt = 0; // default time limit is infinity
        timeLimit = (EditText) findViewById(R.id.timeLimit);

        size = 1; // default size is medium
        small = (Button) findViewById(R.id.Small);
        medium = (Button) findViewById(R.id.Medium);
        large = (Button) findViewById(R.id.Large);
        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size = 0;
                small.setBackgroundColor(Color.BLACK);
                medium.setBackgroundColor(Color.LTGRAY);
                large.setBackgroundColor(Color.LTGRAY);
                small.setTextColor(Color.WHITE);
                medium.setTextColor(Color.BLACK);
                large.setTextColor(Color.BLACK);
            }
        });
        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size = 1;
                small.setBackgroundColor(Color.LTGRAY);
                medium.setBackgroundColor(Color.BLACK);
                large.setBackgroundColor(Color.LTGRAY);
                small.setTextColor(Color.BLACK);
                medium.setTextColor(Color.WHITE);
                large.setTextColor(Color.BLACK);
            }
        });
        large.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size = 2;
                small.setBackgroundColor(Color.LTGRAY);
                medium.setBackgroundColor(Color.LTGRAY);
                large.setBackgroundColor(Color.BLACK);
                small.setTextColor(Color.BLACK);
                medium.setTextColor(Color.BLACK);
                large.setTextColor(Color.WHITE);
            }
        });

        color = 1; // default color is green
        green = (Button) findViewById(R.id.Green);
        red = (Button) findViewById(R.id.Red);
        blue = (Button) findViewById(R.id.Blue);
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = 1;
                green.setBackgroundColor(Color.parseColor("#31a810"));
                green.setTextColor(Color.WHITE);
                red.setBackgroundColor(Color.LTGRAY);
                red.setTextColor(Color.parseColor("#c12c2c"));
                blue.setBackgroundColor(Color.LTGRAY);
                blue.setTextColor(Color.parseColor("#2c3ac1"));
            }
        });
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = 2;
                green.setBackgroundColor(Color.LTGRAY);
                green.setTextColor(Color.parseColor("#31a810"));
                red.setBackgroundColor(Color.parseColor("#c12c2c"));
                red.setTextColor(Color.WHITE);
                blue.setBackgroundColor(Color.LTGRAY);
                blue.setTextColor(Color.parseColor("#2c3ac1"));
            }
        });
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = 3;
                green.setBackgroundColor(Color.LTGRAY);
                green.setTextColor(Color.parseColor("#31a810"));
                red.setBackgroundColor(Color.LTGRAY);
                red.setTextColor(Color.parseColor("#c12c2c"));
                blue.setBackgroundColor(Color.parseColor("#2c3ac1"));
                blue.setTextColor(Color.WHITE);
            }
        });

        sequence = new ArrayList<Integer>();
        sequential = (Button) findViewById(R.id.sequential);
        random = (Button) findViewById(R.id.random);
        customize = (Button) findViewById((R.id.Customize));
        sequential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sequential.setBackgroundColor(Color.BLACK);
                sequential.setTextColor(Color.WHITE);
                random.setBackgroundColor(Color.LTGRAY);
                random.setTextColor(Color.BLACK);
                customize.setBackgroundColor(Color.LTGRAY);
                customize.setTextColor(Color.BLACK);
                sequence.clear();
                for (int i=0; i<9; i++) {
                    sequence.add(i);
                }
            }
        });

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sequential.setBackgroundColor(Color.LTGRAY);
                sequential.setTextColor(Color.BLACK);
                random.setBackgroundColor(Color.BLACK);
                random.setTextColor(Color.WHITE);
                customize.setBackgroundColor(Color.LTGRAY);
                customize.setTextColor(Color.BLACK);
                sequence.clear();
                for (int i = 0; i < 24; i++) {
                    Random r = new Random();
                    int position = r.nextInt(9);
                    sequence.add(position);
                }
            }
        });

        popupSeq = new Dialog(this);
        popupSeq.setContentView(R.layout.seq_enter_popup);
        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sequential.setBackgroundColor(Color.LTGRAY);
                sequential.setTextColor(Color.BLACK);
                random.setBackgroundColor(Color.LTGRAY);
                random.setTextColor(Color.BLACK);
                customize.setBackgroundColor(Color.BLACK);
                customize.setTextColor(Color.WHITE);
                seqIndex = (EditText) popupSeq.findViewById(R.id.seqIndex);
                popupSave = (Button) popupSeq.findViewById(R.id.saveSeq);
                popupSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        seqInt = seqIndex.getText().toString();
                        if (!seqInt.isEmpty() && onlyDigits(seqInt) && noNine(seqInt)) {
                            sequence.clear();
                            for (int i=0; i<seqInt.length(); i++) {
                                sequence.add(seqInt.charAt(i) - '0');
                            }
                            seqIndex.setHintTextColor(Color.LTGRAY);
                            seqIndex.setHint("Each entry should less than 9; No need to separate each entry");
                            popupSeq.dismiss();
                        }else{
                            seqIndex.setHintTextColor(Color.RED);
                            seqIndex.setHint("Please enter and use the correct format");
                            seqIndex.setText("");
                        }
                    }
                });
               popupSeq.show();
            }
        });

        consoleSave = (Button) findViewById(R.id.consoleSave);
        consoleSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLightLmt = lightLimit.getText().toString();
                checkTimeLmt = timeLimit.getText().toString();
                if (!onlyDigits(checkLightLmt)) {
                    lightLimit.setHintTextColor(Color.RED);
                    lightLimit.setHint("Please use the correct format");
                    lightLimit.setText("");
                }
                if (!onlyDigits(checkTimeLmt)) {
                    timeLimit.setHintTextColor(Color.RED);
                    timeLimit.setHint("Please use the correct format");
                    timeLimit.setText("");
                }
                if (onlyDigits(checkLightLmt) && onlyDigits(checkTimeLmt)) {
                    lightLmt = Integer.parseInt(checkLightLmt);
                    timeLmt = Integer.parseInt(checkTimeLmt);
                    lightLimit.setHintTextColor(Color.LTGRAY);
                    lightLimit.setHint("Just enter the integer you want");
                    timeLimit.setHintTextColor(Color.LTGRAY);
                    timeLimit.setHint("Do not enter if you don't want a time limit");
                    Log.i("sequence0: ", sequence.toString());
                    attr = new Attribute(sequence, size, timeLmt, color, lightLmt);
                    Intent toCircleClick = new Intent(getApplicationContext(), CircleClick.class);
                    toCircleClick.putExtra("Attributes", attr);
                    startActivity(toCircleClick);
                }
            }
        });
    }

    public boolean onlyDigits(String s) {
        return s.matches("[0-9]+");
    }

    public boolean noNine(String s) {
        return s.matches("[0-8]+");
    }
}
