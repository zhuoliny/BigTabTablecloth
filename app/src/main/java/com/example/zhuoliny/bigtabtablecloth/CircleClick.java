package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CircleClick extends Activity {

    private UserInfo user;
    private Button buttons[];
    private int marker;
    private Attribute attr;
    private HashMap<Integer, Integer> onOff, tracker;
    private ArrayList<Integer> sequence;
    private int size, color, lightLmt, timeLmt, mode, cycleLmt;
    private writeCSV outputData;
    private int xPosition, yPosition;
    private String finalResult2 = "", finalResult1 = "";
    ArrayList<Touch> allTouch;
    Dialog gameoverPopup, passPopup, resultsPopup, resultsView;
    Button saveYes, saveNo, pass, passYes, passNo, viewYes, viewNo, resultClose;
    private Calendar _cal = Calendar.getInstance();
    private int _month = _cal.get(Calendar.MONTH) + 1;
    private int _day = _cal.get(Calendar.DAY_OF_MONTH);
    private int _year = _cal.get(Calendar.YEAR);
    private int _hour = _cal.get(Calendar.HOUR_OF_DAY);
    private int _minute = _cal.get(Calendar.MINUTE);
    private int _second = _cal.get(Calendar.SECOND);
    static int radius;
    static ArrayList<int[]> positions;
    static private boolean alreadySetup, onTarget;
    private int _touchCount;
    private double _duration;
    private double _accuracy, _accuracyL, _accuracyR, _positionError, _positionErrorL, _positionErrorR;
    EditText _tCount, _dura, _acc, _accL, _accR, _pError, _pErrorL, _pErrorR;
    private Runnable r;
    private Handler timer;
    boolean timerOn=false;
    private int[] passPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView _blank = new TextView(this);
        _blank.setGravity(Gravity.CENTER);
        _blank.setText("Start in 3...2...1...");
        _blank.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
        _blank.setTextColor(Color.parseColor("#2c44c1"));
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
        cycleLmt = attr.getCycleLimit();
        mode = attr.getMode();

        allTouch = new ArrayList<Touch>();
        tracker = new HashMap<Integer, Integer>();
        onOff = new HashMap<Integer, Integer>();

        // game on
        positions = new ArrayList<int[]>();
        buttons = new Button[9];
        alreadySetup = false;
        onTarget = false;
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_circle_click);

                pass = (Button) findViewById(R.id.pass);
                pass.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        allTouch.remove(allTouch.size()-1);
                        viewResults();
                        //passGame();
                    }
                });

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
                lightsUp();
                ToneGenerator beep = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
                beep.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 150);
            }
        }, 3000);

        if (timeLmt > 0) {
            timerOn = true;
            timer = new Handler();
            timer.postDelayed(r = new Runnable() {
                @Override
                public void run() {
                    viewResults();
                }
            }, (timeLmt * 1000 + 3000));
        }
    }

    public void setupLights() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        final RelativeLayout layoutOuter = (RelativeLayout) findViewById(R.id.outerSquare);
        ViewGroup.LayoutParams paramsOuter = layoutOuter.getLayoutParams();
        paramsOuter.height = height * 7 / 8;
        paramsOuter.width = height * 7 / 8;
        layoutOuter.setLayoutParams(paramsOuter);

        final RelativeLayout layoutInner = (RelativeLayout) findViewById(R.id.innerSquare);
        ViewGroup.LayoutParams paramsInner = layoutInner.getLayoutParams();
        paramsInner.height = height * 2 / 3;
        paramsInner.width = height * 2 / 3;
        layoutInner.setLayoutParams(paramsInner);

        radius = height * 2 / 3;

        for (int i = 0; i < buttons.length; i++) {
            if (size == 0) {
                ViewGroup.LayoutParams params = buttons[i].getLayoutParams();
                params.width = radius / 6;
                params.height = radius / 6;
                buttons[i].setLayoutParams(params);
            }
            if (size == 1) {
                ViewGroup.LayoutParams params = buttons[i].getLayoutParams();
                params.width = radius / 5;
                params.height = radius / 5;
                buttons[i].setLayoutParams(params);
            }
            if (size == 2) {
                ViewGroup.LayoutParams params = buttons[i].getLayoutParams();
                params.width = radius / 4;
                params.height = radius / 4;
                buttons[i].setLayoutParams(params);
            }
            buttons[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (onOff.get(v.getId()) == 1 && event.getAction() == MotionEvent.ACTION_DOWN) {
                        v.setBackgroundResource(R.drawable.round_button_grey);
                        onOff.put(v.getId(), 0);
                        onTarget = true;
                        allTouch.get(allTouch.size() - 1).set_onTarget(onTarget);
                        marker++;
                        if (marker < lightLmt) {
                            lightsUp();
                        } else {
                            viewResults();
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
        for (int i = 0; i < buttons.length; i++) {
            onOff.put(buttons[i].getId(), 0);
            tracker.put(buttons[i].getId(), i);
        }
    }

    public void lightsUp() {
        if (sequence.size() < lightLmt) {
            int firstRoundLimit = sequence.size();
            int round = lightLmt / firstRoundLimit;
            int left = lightLmt - firstRoundLimit * round;
            for (int r = 0; r < round; r++) {
                for (int i = 0; i < firstRoundLimit; i++) {
                    sequence.add(sequence.get(i));
                }
            }
            for (int l = 0; l < left; l++) {
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
        if (timerOn) {
            timer.removeCallbacks(r);
        }
        gameoverPopup = new Dialog(this);
        gameoverPopup.setCanceledOnTouchOutside(false);
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

    public void viewResults() {
        resultsPopup = new Dialog(this);
        resultsPopup.setContentView(R.layout.view_results_popup);
        resultsPopup.setCanceledOnTouchOutside(false);
        viewYes = (Button) resultsPopup.findViewById(R.id.viewYes);
        viewYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataAnalysis(allTouch, positions);
                showResults();
                resultsPopup.dismiss();
            }
        });

        viewNo = (Button) resultsPopup.findViewById(R.id.viewNo);
        viewNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataAnalysis(allTouch, positions);
                gameOver();
                resultsPopup.dismiss();
            }
        });
        resultsPopup.show();
    }

    public void showResults() {
        resultsView = new Dialog(this);
        resultsView.setContentView(R.layout.results_page);
        resultsView.setCanceledOnTouchOutside(false);

        _tCount = (EditText) resultsView.findViewById(R.id.countTouch);
        _tCount.setText(Integer.toString(_touchCount));
        _tCount.setFocusable(false);
        _tCount.setFocusableInTouchMode(false);
        _tCount.setClickable(false);

        _dura = (EditText) resultsView.findViewById(R.id.duration);
        _dura.setText(Double.toString(_duration));
        _dura.setFocusable(false);
        _dura.setFocusableInTouchMode(false);
        _dura.setClickable(false);

        _acc = (EditText) resultsView.findViewById(R.id.accuracy);
        _acc.setText(Double.toString(_accuracy));
        _acc.setFocusable(false);
        _acc.setFocusableInTouchMode(false);
        _acc.setClickable(false);

        _accL = (EditText) resultsView.findViewById(R.id.accuracyL);
        _accL.setText(Double.toString(_accuracyL));
        _accL.setFocusable(false);
        _accL.setFocusableInTouchMode(false);
        _accL.setClickable(false);

        _accR = (EditText) resultsView.findViewById(R.id.accuracyR);
        _accR.setText(Double.toString(_accuracyR));
        _accR.setFocusable(false);
        _accR.setFocusableInTouchMode(false);
        _accR.setClickable(false);

        _pError = (EditText) resultsView.findViewById(R.id.avgPE);
        _pError.setText(Double.toString(_positionError));
        _pError.setFocusable(false);
        _pError.setFocusableInTouchMode(false);
        _pError.setClickable(false);

        _pErrorL = (EditText) resultsView.findViewById(R.id.avgPEL);
        _pErrorL.setText(Double.toString(_positionErrorL));
        _pErrorL.setFocusable(false);
        _pErrorL.setFocusableInTouchMode(false);
        _pErrorL.setClickable(false);

        _pErrorR= (EditText) resultsView.findViewById(R.id.avgPER);
        _pErrorR.setText(Double.toString(_positionErrorR));
        _pErrorR.setFocusable(false);
        _pErrorR.setFocusableInTouchMode(false);
        _pErrorR.setClickable(false);

        resultClose = (Button) resultsView.findViewById(R.id.close);
        resultClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameOver();
                resultsView.dismiss();
            }
        });

        resultsView.show();
    }

    /*
    public void passGame() {
        passPopup = new Dialog(this);
        passPopup.setContentView(R.layout.pass_popup);
        passPopup.setCanceledOnTouchOutside(false);
        passYes = (Button) passPopup.findViewById(R.id.passYes);
        passYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewResults();
                passPopup.dismiss();
            }
        });
        passNo = (Button) passPopup.findViewById(R.id.passNo);
        passNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passPopup.dismiss();
            }
        });
        passPopup.show();
    }
    */

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            xPosition = (int) event.getRawX();
            yPosition = (int) event.getRawY();
            Calendar cal = Calendar.getInstance();
            int hour = cal.get(Calendar.HOUR_OF_DAY);
            int minute = cal.get(Calendar.MINUTE);
            int second = cal.get(Calendar.SECOND);
            int mSecond = cal.get(Calendar.MILLISECOND);
            long msTime = System.currentTimeMillis();
            String time_temp = String.valueOf(hour) + ":" + String.valueOf(minute) + ":" + String.valueOf(second) + ":" + String.valueOf(mSecond);
            Touch newTouch = new Touch(time_temp, msTime, xPosition, yPosition);
            newTouch.set_target(tracker.get(buttons[sequence.get(marker)].getId()));
            if (!allTouch.isEmpty()) {
                Touch previous = allTouch.get(allTouch.size() - 1);
                long timeGap_temp = 0;
                timeGap_temp = msTime - previous.get_mSecond();
                newTouch.set_timeGap(timeGap_temp);
                double dist_temp = (xPosition - previous.get_xPosition()) * (xPosition - previous.get_xPosition()) + (yPosition - previous.get_yPosition()) * (yPosition - previous.get_yPosition());
                dist_temp = Math.sqrt(dist_temp);
                newTouch.set_distance(dist_temp);
                newTouch.set_speed();
            }
            allTouch.add(newTouch);
            onTarget = false;
            if (!alreadySetup) {
                for (int i = 0; i < buttons.length; i++) {
                    int temp[] = new int[2];
                    buttons[i].getLocationOnScreen(temp);
                    positions.add(temp);
                }
                alreadySetup = true;
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void saveTouchData() {
        String fileName =
                String.valueOf(_month) + "-" + String.valueOf(_day) + "-" + String.valueOf(_year) + "_" + String.valueOf(_hour) + ":" + String.valueOf(_minute) + ":" + String.valueOf(_second);
        outputData = new writeCSV();
        outputData.writeCsvFile(this, "General"+fileName+".csv", allTouch, user);
        String header1 = "Sequence,TestTime,#ofTouches,TestDuration,Accuracy,AccuracyLeft,AccuracyRight,PositionError,PositionErrorLeft,PositionErrorRight";
        write_readCSV wrCSV1 = new write_readCSV();
        ArrayList<String> temp1 = wrCSV1.readCsvFile("TbClth_"+user.getName()+"/"+"General_AllData.csv");
        ArrayList<String> results1 = new ArrayList<String>();
        if (!temp1.isEmpty()) {
            results1.addAll(temp1);
            results1.add(sequence.toString().replaceAll(",", "")+","+fileName+","+finalResult1);
        }else{
            results1.add(header1);
            results1.add(sequence.toString().replaceAll(",", "")+","+fileName+","+finalResult1);
        }
        wrCSV1.writeCsvFile(this, "General_AllData.csv", results1, user.getName());
        if (mode==2 || mode==3 || mode==4) {
            String header2 = "Sequence,TestTime,Cycle1Duration,Cycle2Duration,Cycle3Duration,Cycle4Duration,Cycle5Duration,Cycle6Duration,Cycle7Duration,Cycle8Duration,CenterToTarget1,CenterToTarget2,CenterToTarget3,CenterToTarget4,CenterToTarget5,CenterToTarget6,CenterToTarget7,CenterToTarget8,TargetToCenter1,TargetToCenter2,TargetToCenter3,TargetToCenter4,TargetToCenter5,TargetToCenter6,TargetToCenter7,TargetToCenter8,ErrorCount";
            write_readCSV wrCSV2 = new write_readCSV();
            ArrayList<String> temp2 = wrCSV2.readCsvFile("TbClth_"+user.getName()+"/"+"Centralized_AllData.csv");
            ArrayList<String> results2 = new ArrayList<String>();
            if (!temp2.isEmpty()) {
                results2.addAll(temp2);
                results2.add(sequence.toString().replaceAll(",", "")+","+fileName+","+finalResult2);
            }else {
                results2.add(header2);
                results2.add(sequence.toString().replaceAll(",", "")+","+fileName + "," + finalResult2);
            }
            wrCSV2.writeCsvFile(this, "Centralized_AllData.csv", results2, user.getName());
        }
    }

    public void dataAnalysis(ArrayList<Touch> allTouch, ArrayList<int[]> xyPs) {
        if (!allTouch.isEmpty()) {
            _touchCount = allTouch.size();
            long dura = allTouch.get(allTouch.size() - 1).get_mSecond() - allTouch.get(0).get_mSecond();
            _duration = (double) (dura / 1000.0);
            double countOnTargetL = 0, countOnTargetR = 0, countOnTarget = 0;
            double countTargetL = 0, countTargetR = 0;
            _positionError = 0;
            _positionErrorL = 0;
            _positionErrorR = 0;
            for (int i = 0; i < allTouch.size(); i++) {
                if (allTouch.get(i).get_target() == 2 || allTouch.get(i).get_target() == 3 || allTouch.get(i).get_target() == 4) {
                    countTargetL++;
                }
                if (allTouch.get(i).get_target() == 6 || allTouch.get(i).get_target() == 7 || allTouch.get(i).get_target() == 8) {
                    countTargetR++;
                }
                if (allTouch.get(i).get_onTarget()) {
                    countOnTarget++;
                    if (allTouch.get(i).get_target() == 2 || allTouch.get(i).get_target() == 3 || allTouch.get(i).get_target() == 4) {
                        countOnTargetL++;
                    }
                    if (allTouch.get(i).get_target() == 6 || allTouch.get(i).get_target() == 7 || allTouch.get(i).get_target() == 8) {
                        countOnTargetR++;
                    }
                } else {
                    int target = allTouch.get(i).get_target();
                    int[] tarP = xyPs.get(target);
                    int touchxP = allTouch.get(i).get_xPosition();
                    int touchyP = allTouch.get(i).get_yPosition();
                    double dist_temp = (touchxP - tarP[0]) * (touchxP - tarP[0]) + (touchyP - tarP[1]) * (touchyP - tarP[1]);
                    dist_temp = Math.sqrt(dist_temp);
                    _positionError = _positionError + dist_temp;
                    if (allTouch.get(i).get_target() == 2 || allTouch.get(i).get_target() == 3 || allTouch.get(i).get_target() == 4) {
                        _positionErrorL = _positionErrorL + dist_temp;
                    }
                    if (allTouch.get(i).get_target() == 6 || allTouch.get(i).get_target() == 7 || allTouch.get(i).get_target() == 8) {
                        _positionErrorR = _positionErrorR + dist_temp;
                    }
                }
            }
            _accuracy = countOnTarget / _touchCount;
            _accuracyL = countOnTargetL / countTargetL;
            _accuracyR = countOnTargetR / countTargetR;

            if (_touchCount != countOnTarget) {
                _positionError = _positionError / (_touchCount - countOnTarget);
            }
            if (countOnTargetL != countTargetL) {
                _positionErrorL = _positionErrorL / (countTargetL - countOnTargetL);
            }
            if (countOnTargetR != countTargetR) {
                _positionErrorR = _positionErrorR / (countTargetR - countOnTargetR);
            }
            finalResult1 = Integer.toString(_touchCount)+","+Double.toString(_duration)+","+Double.toString(_accuracy)+","+Double.toString(_accuracyL)+","+Double.toString(_accuracyR)+","+Double.toString(_positionError)+","+Double.toString(_positionErrorL)+","+Double.toString(_positionErrorR);

            long[] centerToTarget = new long[9];
            long[] targetToCenter = new long[9];
            int[] tempCTT = new int[9];
            int[] tempTTC = new int[9];
            ArrayList<Long> cycleDuration = new ArrayList<Long>();
            int countCycle = 0;
            int cyclePointer = 0;
            if (mode == 2 || mode == 3 || mode == 4) {
                if (allTouch.get(0).get_onTarget()) {
                    countCycle++;
                }
                for (int i = 1; i<allTouch.size(); i++) {
                    if (allTouch.get(i).get_onTarget()) {
                        countCycle++;
                    }
                    if (countCycle == cycleLmt) {
                        cycleDuration.add(allTouch.get(i).get_mSecond()-allTouch.get(cyclePointer).get_mSecond());
                        cyclePointer = i;
                        countCycle = 0;
                    }
                    if (i%2!=0) {
                        centerToTarget[allTouch.get(i).get_target()] = centerToTarget[allTouch.get(i).get_target()] + allTouch.get(i).get_mSecond()-allTouch.get(i-1).get_mSecond();
                        tempCTT[allTouch.get(i).get_target()]++;
                    }else{
                        if (i%2==0) {
                            targetToCenter[allTouch.get(i-1).get_target()] = targetToCenter[allTouch.get(i-1).get_target()] + allTouch.get(i).get_mSecond()-allTouch.get(i-1).get_mSecond();
                            tempTTC[allTouch.get(i-1).get_target()]++;
                        }
                    }
                }
                for (int i=1; i<9; i++) {
                    if (tempCTT[i]!=0) {
                        centerToTarget[i] = centerToTarget[i] / tempCTT[i];
                    }
                    if (tempTTC[i]!=0) {
                        targetToCenter[i] = targetToCenter[i] / tempTTC[i];
                    }
                    finalResult2 += Long.toString(centerToTarget[i]) + "," + Long.toString(targetToCenter[i]) + ",";
                }
                while (cycleDuration.size()<8) {
                    cycleDuration.add(Long.MIN_VALUE);
                }
                String temp = "";
                for (int i=0; i<cycleDuration.size(); i++) {
                    temp += Long.toString(cycleDuration.get(i));
                    temp += ",";
                }
                finalResult2 = temp + finalResult2 + Integer.toString(allTouch.size()-(int)countOnTarget);
            }
        }
    }
}