package com.example.zhuoliny.bigtabtablecloth;

public class aTouch {

    private int _targetIndex;
    private String _time;
    private float _xPosition, _yPosition;
    private double _timeGap;
    private boolean _onTarget;
    private double _distance;  // The distance from the last touch position
    private double _speed;     // _speed = _distance / _timeGap
    private float _mSecond;    // Just for calculating the time gap not showing in the GUI report

    public aTouch (int targetIndex, String time, float xPosition, float yPosition, double timeGap, boolean onTarget, double distance, float mSecond) {
        _targetIndex = targetIndex;
        _time = time;
        _xPosition = xPosition;
        _yPosition = yPosition;
        _timeGap = timeGap;
        _onTarget = onTarget;
        _distance = distance;
        _speed = _distance / _timeGap;
    }

    public int getTargetIndex() {
        return  _targetIndex;
    }

    public float getXPosition() {
        return _xPosition;
    }

    public float getYPostion() {
        return  _yPosition;
    }

    public double getTimeGap() {
        return  _timeGap;
    }

    public String getTime() {
        return _time;
    }

    public boolean hitOrMiss() {
        return _onTarget;
    }

    public double getDistance() {
        return  _distance;
    }

    public double getSpeed() {
        return _speed;
    }

    public float getMSecond() {
        return _mSecond;
    }
}
