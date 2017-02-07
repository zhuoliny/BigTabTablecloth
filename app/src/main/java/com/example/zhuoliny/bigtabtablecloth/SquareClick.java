package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class SquareClick extends Activity {
    private GridLayout board;
    private int rowNumb = 4;
    private int colNumb = 8;
    private TextView[] cards;
    private int[] onOff;
    private int sequenceType;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_click);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent receiver = getIntent();
        sequenceType = (int) receiver.getSerializableExtra("type");
        count = 0;

        board = (GridLayout) findViewById(R.id.board);
        board.setRowCount(rowNumb);
        board.setColumnCount(colNumb);
        board.setOrientation(GridLayout.HORIZONTAL);

        cards = new TextView[rowNumb*colNumb];
        onOff = new int[rowNumb*colNumb];
        for (int y=0;y<rowNumb;y++) {
            for (int x=0;x<colNumb;x++) {
                onOff[y*colNumb+x] = 0;
                TextView card = new TextView(board.getContext());
                card.setText("  ");
                card.setId(y*colNumb+x);
                card.setGravity(Gravity.CENTER);
                card.setBackgroundColor(Color.GRAY);
                if (sequenceType == 0) {
                    card.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            if (onOff[v.getId()] == 1 && event.getAction() == MotionEvent.ACTION_DOWN) {
                                if (v.getId() + 1 < onOff.length) {
                                    v.setBackgroundColor(Color.GRAY);
                                    onOff[v.getId()] = 0;
                                    onOff[v.getId() + 1] = 1;
                                    board.getChildAt(v.getId() + 1).setBackgroundColor(Color.GREEN);
                                    return true;
                                } else {
                                    gameOver();
                                }
                            }
                            return false;
                        }
                    });
                }else{
                    if (sequenceType == 1) {
                        card.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (onOff[v.getId()] == 1 && event.getAction() == MotionEvent.ACTION_DOWN) {
                                    count++;
                                //    Log.i("count: ", Integer.toString(count));
                                    if (count < board.getChildCount()-1) {
                                        v.setBackgroundColor(Color.GRAY);
                                        onOff[v.getId()] = 0;
                                        gameOn();
                                        return true;
                                    }else{
                                        for (int i=0;i<board.getChildCount();i++) {
                                            board.getChildAt(i).setBackgroundColor(Color.GRAY);
                                        }
                                        gameOver();
                                    }
                                }
                                return false;
                            }
                        });
                    }
                }
                cards[y*colNumb+x] = card;
                board.addView(card);
            }
        }
        setLayout();
        gameOn();
    }

    public void setLayout() {
        final int w = 199;
        final int h = 199;
        final int MARGIN = 8;

        for (int y=0;y<rowNumb;y++) {
            for (int x=0;x<colNumb;x++) {
                GridLayout.LayoutParams params =
                        (GridLayout.LayoutParams)cards[y*colNumb+x].getLayoutParams();
                params.width = w - 2*MARGIN;
                params.height = h - 2*MARGIN;
                params.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
                cards[y*colNumb+x].setLayoutParams(params);
            }
        }
    }

    public void gameOn() {
        if (sequenceType == 0) {
            board.getChildAt(board.getChildCount()-1).setBackgroundColor(Color.GRAY);
            board.getChildAt(0).setBackgroundColor(Color.GREEN);
            onOff[0] = 1;
        }else{
            if (sequenceType == 1) {
                Random r = new Random();
                int i = r.nextInt(board.getChildCount()-1);
                board.getChildAt(i).setBackgroundColor(Color.GREEN);
                onOff[i] = 1;
            }
        }
    }

    public void gameOver() {
        Context c = getApplicationContext();
        CharSequence text = "Congrats! The Game Is Completed!";
        int time = Toast.LENGTH_LONG;
        Toast t = Toast.makeText(c, text, time);
        t.show();
        count = 0;
        gameOn();
    }
}