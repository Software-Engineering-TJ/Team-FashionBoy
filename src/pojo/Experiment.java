package pojo;

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

    @Override
    public String toString() {
        return "Experiment{" +
                "courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", expname='" + expname + '\'' +
                '}';
    }
}
