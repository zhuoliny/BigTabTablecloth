package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class CircleClick extends Activity{

    private UserInfo user;
    private Button buttons[];
    private int marker;
    private Attribute attr;
    private HashMap<Integer, Integer> onOff, tracker;
    private ArrayList<Integer> sequence;
    private int size, color, lightLmt, timeLmt;
    //private saveData data;
    private writeCSV outputData;
    private float xPosition, yPosition;
    private long timeInMS;
    ArrayList<String> allTouch;
    Dialog gameoverPopup;
    Button saveYes, saveNo;
    private Calendar _cal = Calendar.getInstance();
    private int _month = _cal.get(Calendar.MONTH)+1;
    private int _day = _cal.get(Calendar.DAY_OF_MONTH);
    private int _year = _cal.get(Calendar.YEAR);
    private int _hour = _cal.get(Calendar.HOUR_OF_DAY);
    private int _minute = _cal.get(Calendar.MINUTE);
    private int _second = _cal.get(Calendar.SECOND);
    RelativeLayout _blank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _blank = new RelativeLayout(this);
        setContentView(_blank);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent userReciver = getIntent();
        user = (UserInfo) userReciver.getSerializableExtra("aUser");

        Intent receiver = getIntent();
        attr = (Attribute) receiver.getSerializableExtra("Attributes");
        marker = 0;
        sequence = attr.getSequence();
        size = attr.getSize();
        color = attr.getColor();
        lightLmt = attr.getLightLimit();
        timeLmt = attr.getTimeLimit();

        allTouch = new ArrayList<String>();
        tracker = new HashMap<Integer, Integer>();
        onOff = new HashMap<Integer, Integer>();

        //game 3-2-1 countdown
        for (int i=3;i>0;i--) {
            Context c = getApplicationContext();
            CharSequence text = Integer.toString(i);
            int time = Toast.LENGTH_SHORT;
            Toast t = Toast.makeText(c, text, time);
            t.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
            t.show();
        }
        Context c = getApplicationContext();
        CharSequence text = "Go!";
        int time = Toast.LENGTH_SHORT;
        Toast t = Toast.makeText(c, text, time);
        t.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        t.show();

        // game on
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_circle_click);
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
                signOnOff();
                setupLights();
                lightsUp(marker);
                ToneGenerator beep = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                beep.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 150);
            }
        }, 8000);
    }

    public void setupLights() {
        for (int i=0;i<buttons.length;i++) {
            if (size == 0) {
                ViewGroup.LayoutParams params = buttons[i].getLayoutParams();
                params.width = 100;
                params.height = 100;
                buttons[i].setLayoutParams(params);
            }
            if (size == 1) {
                ViewGroup.LayoutParams params = buttons[i].getLayoutParams();
                params.width = 150;
                params.height = 150;
                buttons[i].setLayoutParams(params);
            }
            if (size == 2) {
                ViewGroup.LayoutParams params = buttons[i].getLayoutParams();
                params.width = 200;
                params.height = 200;
                buttons[i].setLayoutParams(params);
            }
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
        if (color == 1) {
            buttons[sequence.get(marker)].setBackgroundResource(R.drawable.round_button_green);
        }
        if (color == 2) {
            buttons[sequence.get(marker)].setBackgroundResource(R.drawable.round_button_red);
        }
        if (color == 3) {
            buttons[sequence.get(marker)].setBackgroundResource(R.drawable.round_button_blue);
        }
        onOff.put(buttons[sequence.get(marker)].getId(), 1);
    }

    public void gameOver() {
        /*
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
        */
        gameoverPopup = new Dialog(this);
        gameoverPopup.setContentView(R.layout.gameover_popup);
        saveYes = (Button) gameoverPopup.findViewById(R.id.saveYes);
        saveYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTouchData();
                Intent backToConsole = new Intent(getApplicationContext(), Console.class);
                backToConsole.putExtra("aUser", user);
                startActivity(backToConsole);
                gameoverPopup.dismiss();
            }
        });

        saveNo = (Button) gameoverPopup.findViewById(R.id.saveNo);
        saveNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToConsole = new Intent(getApplicationContext(), Console.class);
                backToConsole.putExtra("aUser", user);
                startActivity(backToConsole);
                gameoverPopup.dismiss();
            }
        });
        gameoverPopup.show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            xPosition = event.getRawX();
            yPosition = event.getRawY();
            timeInMS= System.currentTimeMillis();
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);
            String aTouch = String.valueOf(hour)+":"+String.valueOf(minute)+":"+String.valueOf(second)+","+String.valueOf(timeInMS)+","+String.valueOf(xPosition)+","+String.valueOf(yPosition);
            allTouch.add(aTouch);
        }
        return super.dispatchTouchEvent(event);
    }

    public void saveTouchData() {
        String fileName = "TbClth_" + user.getName().toString() + "_" + String.valueOf(_month) + "-" + String.valueOf(_day) + "-" + String.valueOf(_year) + "_" + String.valueOf(_hour) + ":" + String.valueOf(_minute) + ":" + String.valueOf(_second) + ".csv";;
        outputData = new writeCSV();
        Log.i("All Touch: ", allTouch.toString());
        outputData.writeCsvFile(this, fileName.toString(), allTouch, user);
    }
}
