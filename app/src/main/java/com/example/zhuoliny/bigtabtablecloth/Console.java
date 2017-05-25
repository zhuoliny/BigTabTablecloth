package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Console extends Activity {
    private Attribute attr;
    private ArrayList<Integer> sequence;
    private int size, color, lightLmt, timeLmt;

    private UserInfo user;
    Button seqRepeat, lgtRepeat;
    Boolean seqORlgt, random;
    int modeChose;
    Button customize;
    Spinner randomList, sequentList;
    Button large, medium, small;
    Button green, red, blue;
    EditText lightLimit, timeLimit;
    Dialog popupSeq, seqView;
    EditText seqIndex, seqLabel;
    Button popupSave, consoleSave, saveSeqToList, viewList, changeUser;
    String seqInt, seqLbl;
    String checkLightLmt, checkTimeLmt;
    LinearLayout seqListView;
    List<savedSequence> savedSequences;
    ArrayList<String> dropDown_sqt, dropDown_rdm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        try {
            // Retrieve the list from internal storage
            savedSequences = new ArrayList<savedSequence>();
            savedSequences.addAll((List<savedSequence>) InternalStorage.readObject(this, "SavedSequences"));
            if (savedSequences.isEmpty() || savedSequences == null) {
                savedSequences = new ArrayList<savedSequence>();
                InternalStorage.writeObject(this, "SavedSequences", savedSequences);
            }
        } catch (IOException e) {
            Log.e("Retrieve Error", e.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("Retrieve Error", e.getMessage());
        }

        random = false;
        lightLmt = -1;
        lightLimit = (EditText) findViewById(R.id.lightLimit);
        seqORlgt = true; // true: light or false: sequence // default: light(true)
        lgtRepeat = (Button) findViewById(R.id.lightLmt);
        lgtRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lgtRepeat.setBackgroundColor(Color.BLACK);
                lgtRepeat.setTextColor(Color.WHITE);
                seqORlgt = true;
                seqRepeat.setBackgroundColor(Color.LTGRAY);
                seqRepeat.setTextColor(Color.BLACK);
            }
        });
        seqRepeat = (Button) findViewById(R.id.sequenceLmt);
        seqRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!random) {
                    seqRepeat.setBackgroundColor(Color.BLACK);
                    seqRepeat.setTextColor(Color.WHITE);
                    seqORlgt = false;
                    lgtRepeat.setBackgroundColor(Color.LTGRAY);
                    lgtRepeat.setTextColor(Color.BLACK);
                }else {
                    Context c = getApplicationContext();
                    CharSequence text = "Please select Sequential Options or CUSTOMIZE for choosing SEQUENCE REPEAT";
                    toaster(c, text);
                }
            }
        });

        timeLmt = 0; // default time limit is infinity
        timeLimit = (EditText) findViewById(R.id.timeLimit);

        size = 2; // default size is large
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

        /*
        sequence = new ArrayList<Integer>();
        sequential = (Button) findViewById(R.id.sequential);
        random = (Button) findViewById(R.id.random);
        customize = (Button) findViewById((R.id.Customize));
        sequential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seqORlgt) {
                    modeChose = 1; // sequential chose
                    sequential.setBackgroundColor(Color.BLACK);
                    sequential.setTextColor(Color.WHITE);
                    random.setBackgroundColor(Color.LTGRAY);
                    random.setTextColor(Color.BLACK);
                    customize.setBackgroundColor(Color.LTGRAY);
                    customize.setTextColor(Color.BLACK);
                    sequence.clear();
                    for (int i = 0; i < 9; i++) {
                        sequence.add(i);
                    }
                }else{
                    Context c = getApplicationContext();
                    CharSequence text = "Please Select LIGHT REPEAT for choosing SEQUENTIAL";
                    int time = Toast.LENGTH_SHORT;
                    Toast t = Toast.makeText(c, text, time);
                    t.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    t.show();
                }
            }
        });

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seqORlgt) {
                    modeChose = 2; // random chose
                    sequential.setBackgroundColor(Color.LTGRAY);
                    sequential.setTextColor(Color.BLACK);
                    random.setBackgroundColor(Color.BLACK);
                    random.setTextColor(Color.WHITE);
                    customize.setBackgroundColor(Color.LTGRAY);
                    customize.setTextColor(Color.BLACK);
                    sequence.clear();
                    sequence.add(generateR());
                }else{
                    Context c = getApplicationContext();
                    CharSequence text = "Please Select LIGHT REPEAT for choosing RANDOM";
                    int time = Toast.LENGTH_SHORT;
                    Toast t = Toast.makeText(c, text, time);
                    t.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    t.show();
                }
            }
        });
        */

        modeChose = 0; // nothing chose
        sequence = new ArrayList<Integer>();
        customize = (Button) findViewById((R.id.Customize));
        sequentList = (Spinner) findViewById(R.id.ModeSequentList);
        dropDown_sqt = new ArrayList<String>();
        dropDown_sqt.add("Sequential Options");
        dropDown_sqt.add("Regular");
        dropDown_sqt.add("Centralized");
        ArrayAdapter<String> sequentAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dropDown_sqt);
        sequentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sequentList.setAdapter(sequentAdapter);

        randomList = (Spinner) findViewById(R.id.ModeRandomList);
        dropDown_rdm = new ArrayList<String>();
        dropDown_rdm.add("Randomized Options");
        dropDown_rdm.add("Regular");
        dropDown_rdm.add("Centralized");
        ArrayAdapter<String> randomAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, dropDown_rdm);
        randomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        randomList.setAdapter(randomAdapter);

        sequentList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {}
                if (position == 1) {
                    modeChose = 0; // sequential-regular
                    randomList.setSelection(0);
                    random = true;
                }
                if (position == 2) {
                    modeChose = 1; // sequential-center
                    randomList.setSelection(0);
                    random = true;
                }
                if (modeChose == 1) {
                    sequence.clear();
                    sequence.add(0);
                    int temp = 1;
                    while (sequence.size()<16) {
                        if (sequence.size()%2!=0) {
                            sequence.add(temp);
                            temp++;
                        }else{
                            sequence.add(0);
                        }
                    }
                }
                if (modeChose == 0) {
                    sequence.clear();
                    for (int i = 0; i < 9; i++) {
                        sequence.add(i);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        randomList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (seqORlgt) {
                    if (position == 0) {}
                    if (position == 1) {
                        modeChose = 2; // random-regular
                        sequence.clear();
                        sequence.add(generateR());
                        sequentList.setSelection(0);
                    }
                    if (position == 2) {
                        modeChose = 3; // random-center
                        sequence.clear();
                        sequence.add(0);
                        sequentList.setSelection(0);
                    }
                }else{
                    randomList.setSelection(0);
                    Context c = getApplicationContext();
                    CharSequence text = "Please Select LIGHT REPEAT for choosing Randomized Options";
                    toaster(c, text);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        popupSeq = new Dialog(this);
        popupSeq.setContentView(R.layout.seq_enter_popup);
        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customize.setBackgroundColor(Color.BLACK);
                customize.setTextColor(Color.WHITE);

                seqView = new Dialog(popupSeq.getContext());
                seqView.setContentView(R.layout.seq_list_view);
                viewList = (Button) popupSeq.findViewById(R.id.viewList);
                viewList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        seqListView = (LinearLayout) seqView.findViewById(R.id.list_sequence);
                        seqListView.removeAllViews();
                        if (savedSequences.size()<=0) {
                            Context c = getApplicationContext();
                            CharSequence text = "No available saved sequence.";
                            toaster(c, text);
                            seqView.dismiss();
                        }else {
                            for (int i = 0; i < savedSequences.size(); i++) {
                                Button aSequence = new Button(seqListView.getContext());
                                aSequence.setId(i);
                                aSequence.setText("Label: " + savedSequences.get(i).getSequenceName() + " ---> Sequence: " + savedSequences.get(i).getSequence());
                                aSequence.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        sequence.clear();
                                        sequence.addAll(savedSequences.get(v.getId()).getSequence());
                                        Context c = getApplicationContext();
                                        CharSequence text = "Sequence: " + savedSequences.get(v.getId()).getSequenceName() + " is chose.";
                                        toaster(c, text);
                                        seqView.dismiss();
                                        popupSeq.dismiss();
                                    }
                                });
                                seqListView.addView(aSequence, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
                            }
                        }
                        seqView.show();
                    }
                });

                seqIndex = (EditText) popupSeq.findViewById(R.id.seqIndex);
                seqIndex.setText("");
                popupSave = (Button) popupSeq.findViewById(R.id.useSeq);
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

                seqLabel = (EditText) popupSeq.findViewById(R.id.seqLabel);
                seqLabel.setText("");
                saveSeqToList = (Button) popupSeq.findViewById(R.id.saveToList);
                saveSeqToList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        seqLbl = seqLabel.getText().toString();
                        seqInt = seqIndex.getText().toString();
                        if (!seqLbl.isEmpty() && !seqInt.isEmpty() && onlyDigits(seqInt) && noNine(seqInt)) {
                            sequence.clear();
                            for (int i=0; i<seqInt.length(); i++) {
                                sequence.add(seqInt.charAt(i) - '0');
                            }
                            savedSequences.add(new savedSequence(seqLbl, sequence));
                            try {
                                InternalStorage.writeObject(popupSeq.getContext(), "SavedSequences", savedSequences);
                                savedSequences.clear();
                                savedSequences.addAll((List<savedSequence>) InternalStorage.readObject(popupSeq.getContext(), "SavedSequences"));
                            } catch (IOException e) {
                                Log.e("Retrieve Error", e.getMessage());
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                            seqIndex.setHintTextColor(Color.LTGRAY);
                            seqIndex.setHint("Each entry should less than 9; No need to separate each entry");
                            popupSeq.dismiss();
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
                if (sequence.isEmpty()) {
                    Context c = getApplicationContext();
                    CharSequence text = "Please Select HOW THE LIGHT ACTS";
                    int time = Toast.LENGTH_SHORT;
                    Toast t = Toast.makeText(c, text, time);
                    t.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    t.show();
                }
                if (onlyDigits(checkLightLmt) && onlyDigits(checkTimeLmt)) {
                    lightLmt = Integer.parseInt(checkLightLmt);
                    if (!seqORlgt) {
                        lightLmt = lightLmt*sequence.size();
                    }else {
                        if (seqORlgt && modeChose==2 || seqORlgt && modeChose==3) {
                            for (int i = 0; i < lightLmt; i++) {
                                if (modeChose==3 && i%2!=0) {
                                    sequence.add(0);
                                }else {
                                    int temp = generateR();
                                    while (temp == sequence.get(sequence.size() - 1)) {
                                        temp = generateR();
                                    }
                                    sequence.add(temp);
                                }
                            }
                        }
                    }
                    timeLmt = Integer.parseInt(checkTimeLmt);
                    lightLimit.setHintTextColor(Color.LTGRAY);
                    lightLimit.setHint("Just enter the integer you want");
                    timeLimit.setHintTextColor(Color.LTGRAY);
                    timeLimit.setHint("Do not enter if you don't want a time limit");
                    Intent receiver = getIntent();
                    user = (UserInfo) receiver.getSerializableExtra("aUser");
                    attr = new Attribute(sequence, size, timeLmt, color, lightLmt, sequence.size(), modeChose);
                    Intent toCircleClick = new Intent(getApplicationContext(), CircleClick.class);
                    toCircleClick.putExtra("Attributes", attr);
                    toCircleClick.putExtra("aUser", user);
                    startActivity(toCircleClick);
                }
            }
        });

        changeUser = (Button) findViewById(R.id.changeUser);
        changeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backward = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backward);
            }
        });
    }

    public int generateR() {
        Random r = new Random();
        return r.nextInt(9);
    }

    public void toaster(Context c, CharSequence text) {
        int time = Toast.LENGTH_SHORT;
        Toast t = Toast.makeText(c, text, time);
        t.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
        t.show();
    }

    public boolean onlyDigits(String s) {
        return s.matches("[0-9]+");
    }

    public boolean noNine(String s) {
        return s.matches("[0-8]+");
    }
}
