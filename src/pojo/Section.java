package pojo;

/**
 * section类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:43
 */

public class Section {
    private String courseID;
    private String classID;

    public Section(){}

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
        return "Section{" +
                "courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                '}';
    }
}
