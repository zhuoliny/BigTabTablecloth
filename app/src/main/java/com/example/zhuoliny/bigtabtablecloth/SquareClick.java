package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SquareClick extends Activity {
    private GridLayout board;
    private RelativeLayout screen;
    private int rowNumb = 7;
    private int colNumb = 14;
    private TextView[] cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_click);

        board = (GridLayout) findViewById(R.id.board);
        board.setRowCount(rowNumb);
        board.setColumnCount(colNumb);
        board.setBackgroundColor(Color.WHITE);

        cards = new TextView[rowNumb*colNumb];
        for (int y=0;y<rowNumb;y++) {
            for (int x=0;x<colNumb;x++) {
                TextView card = new TextView(board.getContext());
                card.setText("  ");
                card.setBackgroundColor(Color.GRAY);
                card.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {
                            v.setBackgroundColor(Color.GREEN);
                            return true;
                        }
                        return false;
                    }
                });
                cards[y*colNumb+x] = card;
                board.addView(card);
            }
        }
        setLayout();
    }

    public void setLayout() {
        final int w = 60;
        final int h = 62;
        final int MARGIN = 3;

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
}
