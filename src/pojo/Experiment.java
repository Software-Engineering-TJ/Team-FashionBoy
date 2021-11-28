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
    private String startDate;
    private String endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", expInfo='" + expInfo + '\'' +
                '}';
    }
}
