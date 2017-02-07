package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SquareClick extends Activity {
    private GridLayout board;
    private int rowNumb = 8;
    private int colNumb = 12;
    private TextView[] cards;
    private int[] onOff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_click);

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
                card.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (onOff[v.getId()] == 1 && event.getAction() == MotionEvent.ACTION_DOWN) {
                            if (v.getId()+1<onOff.length) {
                                v.setBackgroundColor(Color.GRAY);
                                onOff[v.getId()] = 0;
                                onOff[v.getId() + 1] = 1;
                                board.getChildAt(v.getId() + 1).setBackgroundColor(Color.GREEN);
                                return true;
                            }else{
                                Context c = getApplicationContext();
                                CharSequence text = "Congrats! The Game Is Completed!";
                                int time = Toast.LENGTH_LONG;
                                Toast t = Toast.makeText(c, text, time);
                                t.show();
                                gameOn();
                            }
                        }
                        return false;
                    }
                });
                cards[y*colNumb+x] = card;
                board.addView(card);
            }
        }
        setLayout();
        gameOn();
    }

    public void setLayout() {
        final int w = 200;
        final int h = 200;
        final int MARGIN = 5;

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
        board.getChildAt(board.getChildCount()-1).setBackgroundColor(Color.GRAY);
        board.getChildAt(0).setBackgroundColor(Color.GREEN);
        onOff[0] = 1;
    }
}
