package pojo;

import java.util.Date;

/**
 * experiment类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:43
 */

public class Experiment {
    private String courseID;
    private String classID;
    private String expname;
    private Date ddl;
    private float percent;

    public Experiment(){}

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

    public Date getDdl() {
        return ddl;
    }

    public void setDdl(Date ddl) {
        this.ddl = ddl;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "Experiment{" +
                "courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", expname='" + expname + '\'' +
                ", ddl=" + ddl +
                ", percent=" + percent +
                '}';
    }
}
