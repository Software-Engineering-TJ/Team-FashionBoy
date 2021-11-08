package pojo;

import java.util.Date;

/**
 * uploadfile类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:43
 */

public class Uploadfile {
    private String email;
    private String courseID;
    private String classID;
    private String expname;
    private String filename;
    private Date time;

    public Uploadfile(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getExpname() {
        return expname;
    }

    public void setExpname(String expname) {
        this.expname = expname;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Uploadfile{" +
                "email='" + email + '\'' +
                ", courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", expname='" + expname + '\'' +
                ", filename='" + filename + '\'' +
                ", time=" + time +
                '}';
    }
}
