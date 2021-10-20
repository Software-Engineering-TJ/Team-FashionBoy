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

    @Override
    public String toString() {
        return "Course{" +
                "courseID='" + courseID + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
