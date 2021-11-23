package pojo;

/**
 * ExpScore类的描述：
 * 实验成绩
 * @author 黄金坤（HJK）
 * @since 2021/11/23  17:08
 */

public class ExpScore {
    private String studentNumber;
    private String courseID;
    private String expname;
    private String classID;
    private float score;
    private String comment;

    public ExpScore() {
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "ExpScore{" +
                "studentNumber='" + studentNumber + '\'' +
                ", courseID='" + courseID + '\'' +
                ", expname='" + expname + '\'' +
                ", classID='" + classID + '\'' +
                ", score=" + score +
                ", comment='" + comment + '\'' +
                '}';
    }
}
