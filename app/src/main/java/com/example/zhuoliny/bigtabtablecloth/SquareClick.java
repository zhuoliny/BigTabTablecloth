package com.example.zhuoliny.bigtabtablecloth;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

public class SquareClick extends Activity {
    GridView board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_square_click);

        board = (GridView) findViewById(R.id.board);
        board.setAdapter(new ImageAdapter(this));
    }

    public class ImageAdapter extends BaseAdapter {
        Context mC;

        public ImageAdapter (Context c) {
            mC = c;
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (convertView == null) {
                LayoutInflater l = getLayoutInflater();
                view = l.inflate(R.layout.activity_square_click, null);
                view.setBackgroundColor(Color.BLACK);
                Log.e("here", "here!");
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        v.setBackgroundColor(Color.GREEN);
                    }
                });
            }
            return view;
        }
    }
}
