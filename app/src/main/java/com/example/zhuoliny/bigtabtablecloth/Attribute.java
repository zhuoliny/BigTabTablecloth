package com.example.zhuoliny.bigtabtablecloth;

public class Attribute {
    private int[] sequence;
    private int size;
    private int timeLimit;
    private int lightLimit;
    private int color;

    public Attribute (int[] _seq, int _size, int _tmLmt, int _color, int _lgtLmt) {
        sequence = _seq;
        size = _size;
        timeLimit = _tmLmt;
        color = _color;
        lightLimit = _lgtLmt;
    }

    public Attribute (int[] _seq, int _size, int _tmLmt) {
        sequence = _seq;
        size = _size;
        timeLimit = _tmLmt;
    }
}
