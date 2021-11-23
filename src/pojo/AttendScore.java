package pojo;

/**
 * AttendScore类的描述：
 * 签到记录
 * @author 黄金坤（HJK）
 * @since 2021/11/23  17:01
 */

public class AttendScore {
    private String courseID;
    private String classID;
    private String title;
    private String studentNumber;
    private int onTime;  //0：迟到，1：准时签到

    public AttendScore() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public int getOnTime() {
        return onTime;
    }

    public void setOnTime(int onTime) {
        this.onTime = onTime;
    }

    @Override
    public String toString() {
        return "AttendScore{" +
                "courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", title='" + title + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", onTime=" + onTime +
                '}';
    }
}
