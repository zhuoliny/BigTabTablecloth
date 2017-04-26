package com.example.zhuoliny.bigtabtablecloth;

public class Touch {
    private String _time;
    private long _timeGap = 0;
    private long _mSecond;
    private int _xPosition, _yPosition;
    private boolean _onTarget;
    private int _target;
    private double _distance = 0, _speed = 0;

    public Touch (String time, long mSecond, int xPositoon, int yPosition) {
        _time = time;
        _mSecond = mSecond;
        _xPosition = xPositoon;
        _yPosition = yPosition;
        _speed = _distance/_timeGap; 
    }

    public void set_timeGap(long timeGap) {
        _timeGap = timeGap;
    }
    public long get_timeGap() {
        return _timeGap;
    }

    public void set_onTarget(boolean onTarget) {
        _onTarget = onTarget;
    }
    public boolean get_onTarget() {
        return _onTarget;
    }

    public void set_distance(double distance) {
        _distance = distance;
    }
    public double get_distance() {
        return _distance;
    }

    public void set_speed() {
        if (_distance != 0 && _timeGap != 0) {
            _speed = _distance / _timeGap;
        }
    }
    public double get_speed() {
        return _speed;
    }

    public long get_mSecond() {
        return _mSecond;
    }

    public void set_target(int target) {
        _target = target;
    }
    public int get_target() {
        return  _target;
    }

    public String get_time() {
        return _time;
    }

    public int get_xPosition() {
        return _xPosition;
    }
    public int get_yPosition() {
        return _yPosition;
    }
}