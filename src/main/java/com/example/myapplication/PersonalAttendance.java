package com.example.myapplication;

public class PersonalAttendance {

     String studentID;
     Integer attendance;

    public PersonalAttendance() {

    }

    public PersonalAttendance(String studentID, Integer attendance) {
        this.studentID = studentID;
        this.attendance = attendance;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }
}
