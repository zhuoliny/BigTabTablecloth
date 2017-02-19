package com.example.zhuoliny.bigtabtablecloth;

public class Attribute {
    private int[] sequence;
    private int size;
    private int timeLimit;
    private int lightUpLimit;
    private int color;

    public Attribute (int[] _seq, int _size, int _tmLmt, int _color, int _lgtUpLmt) {
        sequence = _seq;
        size = _size;
        timeLimit = _tmLmt;
        color = _color;
        lightUpLimit = _lgtUpLmt;
    }

    public Attribute (int[] _seq, int _size, int _tmLmt) {
        sequence = _seq;
        size = _size;
        timeLimit = _tmLmt;
    }
}
