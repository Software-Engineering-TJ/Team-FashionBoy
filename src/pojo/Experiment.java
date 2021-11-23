package pojo;

import java.util.Date;

/**
 * experiment类的描述：
 * 每个班级根据情况发布的实验，具有截止日期
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:43
 */

public class Experiment {
    private String courseID;
    private String expname;
    private String classID;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String expInfo;

    public Experiment(){}

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getExpname() {
        return expname;
    }

    public void setExpname(String expname) {
        this.expname = expname;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getExpInfo() {
        return expInfo;
    }

    public void setExpInfo(String expInfo) {
        this.expInfo = expInfo;
    }

    @Override
    public String toString() {
        return "Experiment{" +
                "courseID='" + courseID + '\'' +
                ", expname='" + expname + '\'' +
                ", classID='" + classID + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", expInfo='" + expInfo + '\'' +
                '}';
    }
}
