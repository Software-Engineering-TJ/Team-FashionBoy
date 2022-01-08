package pojo;

/**
 * course类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:42
 */

public class Course {
    private String courseID;
    private String title;
    private String instructorNumber;
    private int flag;//标志位：是否通过了管理员审核（0、1）

    public Course(){}

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstructorNumber() {
        return instructorNumber;
    }

    public void setInstructorNumber(String instructorNumber) {
        this.instructorNumber = instructorNumber;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID='" + courseID + '\'' +
                ", title='" + title + '\'' +
                ", instructorNumber='" + instructorNumber + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
