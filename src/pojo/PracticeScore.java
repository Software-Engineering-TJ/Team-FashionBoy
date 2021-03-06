package pojo;

import java.sql.Timestamp;

/**
 * @author HJK
 */
public class PracticeScore {
    private String courseID;
    private String classID;
    private String studentNumber;
    private float individualScore;
    private Timestamp individualTime;
    private int groupNumber;

    public PracticeScore() {
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

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public float getIndividualScore() {
        return individualScore;
    }

    public void setIndividualScore(float individualScore) {
        this.individualScore = individualScore;
    }

    public Timestamp getIndividualTime() {
        return individualTime;
    }

    public void setIndividualTime(Timestamp individualTime) {
        this.individualTime = individualTime;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }


    @Override
    public String toString() {
        return "PracticeScore{" +
                "courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", individualScore=" + individualScore +
                ", individualTime=" + individualTime +
                ", groupNumber=" + groupNumber +
                '}';
    }
}
