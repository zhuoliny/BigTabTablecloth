package com.example.zhuoliny.bigtabtablecloth;

import java.io.Serializable;
import java.util.ArrayList;

public class Attribute implements Serializable{
    private ArrayList<Integer> sequence;
    private int size;
    private int timeLimit;
    private int lightLimit;
    private int color;
    private int cycleLimit;
    private int mode;

    public Attribute (ArrayList<Integer> _seq, int _size, int _tmLmt, int _color, int _lgtLmt, int _cycleLimit, int _mode) {
        sequence = _seq;
        size = _size;
        timeLimit = _tmLmt;
        color = _color;
        lightLimit = _lgtLmt;
        cycleLimit = _cycleLimit;
        mode = _mode;
    }

    public ArrayList<Integer> getSequence() {
        return sequence;
    }

    public int getSize() {
        return size;
    }

    public int getColor() {
        return color;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public int getLightLimit() {
        return lightLimit;
    }

    public int getCycleLimit() { return cycleLimit; }

    public int getMode() { return mode;}
 }
