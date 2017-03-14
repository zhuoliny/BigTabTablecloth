package com.example.zhuoliny.bigtabtablecloth;

import java.io.Serializable;
import java.util.ArrayList;

public class savedSequence implements Serializable{
    private String sequenceName;
    private ArrayList<Integer> sequence;

    public savedSequence(String _name, ArrayList<Integer> _sequence) {
        sequenceName = _name;
        sequence = _sequence;
    }
    public String getSequenceName() {return sequenceName;}
    public ArrayList<Integer> getSequence() {return sequence;}
}
