package pojo;

/**
 * teaches类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:41
 */

public class Teaches {
    private String instructorNumber;
    private String courseID;
    private String classID;

    public Teaches(){}

    public String getInstructorNumber() {
        return instructorNumber;
    }

    public void setInstructorNumber(String instructorNumber) {
        this.instructorNumber = instructorNumber;
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

    @Override
    public String toString() {
        return "Teaches{" +
                "instructorNumber='" + instructorNumber + '\'' +
                ", courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                '}';
    }
}
