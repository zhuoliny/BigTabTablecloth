package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class CircleClick extends Activity {

    private Button buttons[];
    private int marker;
    private Attribute attr;
    private HashMap<Integer, Integer> onOff, tracker;
    private ArrayList<Integer> sequence;
    private int size, color, lightLmt, timeLmt;
    // private int sequenceType; belongs to original method
    // int count; belongs to original method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_click);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent receiver = getIntent();
        attr = (Attribute) receiver.getSerializableExtra("Attributes");
        marker = 0;
        sequence = attr.getSequence();
        size = attr.getSize();
        color = attr.getColor();
        lightLmt = attr.getLightLimit();
        timeLmt = attr.getTimeLimit();

        buttons = new Button[9];
        buttons[0] = (Button) findViewById(R.id.btn0);
        buttons[1] = (Button) findViewById(R.id.btn1);
        buttons[2] = (Button) findViewById(R.id.btn2);
        buttons[3] = (Button) findViewById(R.id.btn3);
        buttons[4] = (Button) findViewById(R.id.btn4);
        buttons[5] = (Button) findViewById(R.id.btn5);
        buttons[6] = (Button) findViewById(R.id.btn6);
        buttons[7] = (Button) findViewById(R.id.btn7);
        buttons[8] = (Button) findViewById(R.id.btn8);

        tracker = new HashMap<Integer, Integer>();
        onOff = new HashMap<Integer, Integer>();

        signOnOff();
        setupLights();
        lightsUp(marker);
    }

    public void setupLights() {
        for (int i=0;i<buttons.length;i++) {
            buttons[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (onOff.get(v.getId()) == 1 && event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setBackgroundResource(R.drawable.round_button_grey);
                        onOff.put(v.getId(), 0);
                        marker++;
                        if (marker < lightLmt) {
                            lightsUp(marker);
                        }else{
                            gameOver();
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
    }
    /* for setup the size
    ViewGroup.LayoutParams params = v.getLayoutParams();
    params.width = 100;
    params.height = 100;
    v.setLayoutParams(params);
    */

    public void signOnOff() {
        onOff.clear();
        for (int i=0;i<buttons.length;i++) {
            onOff.put(buttons[i].getId(), 0);
            tracker.put(buttons[i].getId(), i);
        }
    }

    public void lightsUp(int m) {
        if (sequence.size() < lightLmt) {
            int firstRoundLimit = sequence.size();
            int round = lightLmt / firstRoundLimit;
            int left = lightLmt - firstRoundLimit * round;
            for (int r=0; r<round; r++) {
                for (int i=0; i<firstRoundLimit; i++) {
                    sequence.add(sequence.get(i));
                }
            }
            for (int l=0; l<left; l++) {
                sequence.add(sequence.get(l));
            }
        }
        buttons[sequence.get(marker)].setBackgroundResource(R.drawable.round_button_green);
        onOff.put(buttons[sequence.get(marker)].getId(), 1);
    }

    public void gameOver() {
        for (int i=0;i<buttons.length;i++) {
            buttons[i].setBackgroundResource(R.drawable.round_button_grey);
            onOff.put(buttons[i].getId(), 0);
        }
        Context c = getApplicationContext();
        CharSequence text = "Congrats! The Game Is Completed!";
        int time = Toast.LENGTH_LONG;
        Toast t = Toast.makeText(c, text, time);
        t.show();
        marker = 0;
        lightsUp(marker);
    }

        /* the original method can only handle sequential and random cases
        for (int i=0;i<buttons.length;i++) {
            buttons[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (onOff.get(v.getId()) == 1 && event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setBackgroundResource(R.drawable.round_button_grey);
                        onOff.put(v.getId(), 0);
                        if (sequenceType==1) {
                            gameOn(v.getId());
                            count++;
                        }else{
                            if (sequenceType==0) {
                                int temp = tracker.get(v.getId());
                                if (temp<buttons.length-1) {
                                    buttons[temp+1].setBackgroundResource(R.drawable.round_button_green);
                                    onOff.put(buttons[temp+1].getId(), 1);
                                }else{
                                    gameOver();
                                }
                            }
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
        gameOn(-2693);
    }

    public void gameOver() {
        for (int i=0;i<buttons.length;i++) {
            buttons[i].setBackgroundResource(R.drawable.round_button_grey);
            onOff.put(buttons[i].getId(), 0);
        }
        count = 0;
        Context c = getApplicationContext();
        CharSequence text = "Congrats! The Game Is Completed!";
        int time = Toast.LENGTH_LONG;
        Toast t = Toast.makeText(c, text, time);
        t.show();
        gameOn(-2693);
    }

    public void gameOn(int lastLight) {
        if (sequenceType==0) {
            buttons[0].setBackgroundResource(R.drawable.round_button_green);
            onOff.put(buttons[0].getId(), 1);
        }else{
            if (sequenceType==1) {
                Random r = new Random();
                int i = r.nextInt(buttons.length-1);
                if (count>24) {
                    gameOver();
                }else {
                    if (buttons[i].getId() != lastLight) {
                        buttons[i].setBackgroundResource(R.drawable.round_button_green);
                        onOff.put(buttons[i].getId(), 1);
                    } else {
                        gameOn(lastLight);
                    }
                }
            }
        }
    }
    */
}
