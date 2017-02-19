package com.example.zhuoliny.bigtabtablecloth;

public class UserInfo {
    private String userName;
    private int gender;
    private int age;
    private String moreInfo;

    public UserInfo (String _uName, int _g, int _age, String _more) {
        userName = _uName;
        gender = _g;
        age = _age;
        moreInfo = _more;
    }

    public String getName () {
        return userName;
    }

    public int getGender() {
        return gender; // female(0) male(1)
    }

    public int getAge() {
        return age;
    }

    public String getMoreInfo() {
        return moreInfo;
    }
}
