package pojo;

/**
 * takes类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:41
 */

public class Takes {
    private String studentNumber;
    private String courseID;
    private String classID;
    private int status;

    public Takes(){}

    public String getEmail() {
        return studentNumber;
    }

    public void setEmail(String studentNumber) {
        this.studentNumber = studentNumber;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Takes{" +
                "studentNumber='" + studentNumber + '\'' +
                ", courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", status=" + status +
                '}';
    }
}
