package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class CircleClick extends Activity {

    private Button buttons[];
    private int sequenceType;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_click);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent receiver = getIntent();
        sequenceType = (int) receiver.getSerializableExtra("type");
        count = 0;

        buttons = new Button[8];
        buttons[0] = (Button) findViewById(R.id.btn1);
        buttons[1] = (Button) findViewById(R.id.btn2);
        buttons[2] = (Button) findViewById(R.id.btn3);
        buttons[3] = (Button) findViewById(R.id.btn4);
        buttons[4] = (Button) findViewById(R.id.btn5);
        buttons[5] = (Button) findViewById(R.id.btn6);
        buttons[6] = (Button) findViewById(R.id.btn7);
        buttons[7] = (Button) findViewById(R.id.btn8);

        for (int i=0;i<buttons.length;i++) {
            buttons[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setBackgroundResource(R.drawable.round_button_grey);
                        if (sequenceType==1) {
                            gameOn();
                            count++;
                        }else{
                            if (sequenceType==0) {
                                for (int id=0;id<buttons.length;id++) {
                                    if (buttons[id].getId() == v.getId()) {
                                        if (id<buttons.length-1) {
                                            buttons[id + 1].setBackgroundResource(R.drawable.round_button_green);
                                        }else{
                                            gameOver();
                                        }
                                    }
                                }
                            }
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
        gameOn();
    }

    public void gameOver() {
        for (int i=0;i<buttons.length;i++) {
            buttons[i].setBackgroundResource(R.drawable.round_button_grey);
        }
        Context c = getApplicationContext();
        CharSequence text = "Congrats! The Game Is Completed!";
        int time = Toast.LENGTH_LONG;
        Toast t = Toast.makeText(c, text, time);
        t.show();
        count = 0;
        gameOn();
    }

    public void gameOn() {
        if (sequenceType==0) {
            buttons[0].setBackgroundResource(R.drawable.round_button_green);
        }else{
            if (sequenceType==1) {
                Random r = new Random();
                int i = r.nextInt(buttons.length-1);
                buttons[i].setBackgroundResource(R.drawable.round_button_green);
                if (count>24) {
                    gameOver();
                }
            }
        }
    }

    /*
    private GridLayout rB;
    private Button rButtons[];
    private int colCount, rowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_click);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        colCount=8;
        rowCount=8;

        rB = (GridLayout) findViewById(R.id.roundB);
        rB.setRowCount(rowCount);
        rB.setColumnCount(colCount);
        rB.setOrientation(GridLayout.HORIZONTAL);

        rButtons = new Button[colCount*rowCount];
        setLights();
    }

    public void setLights() {
        for (int i=0;i<rButtons.length;i++) {
            final Button rLight = new Button(rB.getContext());
            rLight.setBackgroundResource(R.drawable.round_button_grey);
            rLight.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        rLight.setBackgroundResource(R.drawable.round_button_green);
                    }
                    return false;
                }
            });
            rButtons[i] = rLight;
            rB.addView(rLight);
        }

        for (int i=0;i<50;i++) {
            Random r = new Random();
            int x = r.nextInt(colCount);
            int y = r.nextInt(rowCount);
            rB.getChildAt(y*colCount+x).setVisibility(View.GONE);
             GridLayout.LayoutParams params =
                     (GridLayout.LayoutParams)rButtons[i].getLayoutParams();
            params.columnSpec = rB.spec(x);
            params.rowSpec = rB.spec(y);
            rButtons[i].setLayoutParams(params);
        }
    }
    */
}
