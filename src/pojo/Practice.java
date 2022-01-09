package pojo;

import java.sql.Timestamp;

/**
 * @author HJK
 */
public class Practice {
    private String courseID;

    private String classID;

    private Timestamp startTime;

    private Timestamp endTime;

    private String practiceName;

    public Practice() {
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

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getPracticeName() {
        return practiceName;
    }

    public void setPracticeName(String practiceName) {
        this.practiceName = practiceName;
    }

    @Override
    public String toString() {
        return "Practice{" +
                "courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", practiceName='" + practiceName + '\'' +
                '}';
    }
}
