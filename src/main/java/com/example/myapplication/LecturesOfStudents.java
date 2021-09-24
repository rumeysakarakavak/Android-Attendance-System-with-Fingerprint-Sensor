package com.example.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class LecturesOfStudents implements Parcelable {

    String lectureId;
    Integer attendanceCount;

    protected LecturesOfStudents(Parcel in) {
        lectureId = in.readString();
        if (in.readByte() == 0) {
            attendanceCount = null;
        } else {
            attendanceCount = in.readInt();
        }
    }

    public static final Creator<LecturesOfStudents> CREATOR = new Creator<LecturesOfStudents>() {
        @Override
        public LecturesOfStudents createFromParcel(Parcel in) {
            return new LecturesOfStudents(in);
        }

        @Override
        public LecturesOfStudents[] newArray(int size) {
            return new LecturesOfStudents[size];
        }
    };

    public String getLectureId() {
        return lectureId;
    }

    public LecturesOfStudents() {
    }

    public void setLectureId(String lectureId) {
        this.lectureId = lectureId;
    }

    public Integer getAttendanceCount() {
        return attendanceCount;
    }

    public void setAttendanceCount(Integer attendanceCount) {
        this.attendanceCount = attendanceCount;
    }

    public LecturesOfStudents(String lectureId, Integer attendanceCount) {
        this.lectureId = lectureId;
        this.attendanceCount = attendanceCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(lectureId);
        if (attendanceCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(attendanceCount);
        }
    }
}
