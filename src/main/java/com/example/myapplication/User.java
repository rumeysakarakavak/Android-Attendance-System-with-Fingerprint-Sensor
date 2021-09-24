package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class User {

    String userId;
    String userName;
    String password;
    //ArrayList<LecturesOfStudents> lectures;
    String userType;

    public User(){

    }
    public User(String userId, String userName, String password, String userType){
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

//    public User(String userId, String userName, String password, ArrayList<LecturesOfStudents> lectures, String userType) {
//        this.userId = userId;
//        this.userName = userName;
//        this.password = password;
//        this.lectures = lectures;
//        this.userType = userType;
//    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

//    public ArrayList<LecturesOfStudents> getLectures() {
//        return lectures;
//    }

    public String getUserType() { return userType;}

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public void setLectures(ArrayList<LecturesOfStudents> lectures) {
//        this.lectures = lectures;
//    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
