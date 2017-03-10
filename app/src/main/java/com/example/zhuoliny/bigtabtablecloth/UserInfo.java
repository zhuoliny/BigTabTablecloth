package com.example.zhuoliny.bigtabtablecloth;

public class UserInfo {
    private String userName;
    private String gender;
    private int age;
    private String moreInfo;

    public UserInfo (String _uName, String _g, int _age, String _more) {
        userName = _uName;
        gender = _g;
        age = _age;
        moreInfo = _more;
    }

    public String getName () {
        return userName;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getMoreInfo() {
        return moreInfo;
    }
}
