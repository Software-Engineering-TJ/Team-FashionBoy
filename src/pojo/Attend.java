package pojo;

/**
 * Attend类的描述：
 * 签到
 * @author 黄金坤（HJK）
 * @since 2021/11/23  16:56
 */

public class Attend {
    private String courseID;
    private String classID;
    private String title;
    private String startTime;
    private String endTime;

    public Attend() {
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Attend{" +
                "courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", title='" + title + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}
