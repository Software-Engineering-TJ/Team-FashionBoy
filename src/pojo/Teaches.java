package pojo;

/**
 * teaches类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:41
 */

public class Teaches {
    private String email;
    private String courseID;
    private String classID;
    private int status;

    public Teaches(){}

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Teaches{" +
                "email='" + email + '\'' +
                ", courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", status=" + status +
                '}';
    }
}
