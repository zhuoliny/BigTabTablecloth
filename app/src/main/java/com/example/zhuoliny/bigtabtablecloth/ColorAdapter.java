package com.example.zhuoliny.bigtabtablecloth;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ColorAdapter extends BaseAdapter {
    private Context context;
    public ColorAdapter(Context c) {
        context = c;
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
        //View cell;
        //cell = new ImageView(context);
        //cell.setBackgroundColor(Color.GREEN);
        //return cell;
        TextView cell;
        if (convertView == null) {
            cell = new TextView(context);
            cell.setLayoutParams(new GridView.LayoutParams(85, 85));
            cell.setPadding(2,2,2,2);
            cell.setBackgroundColor(Color.DKGRAY);
        }else{
            cell = (TextView) convertView;
        }
        return cell;
    }
}
