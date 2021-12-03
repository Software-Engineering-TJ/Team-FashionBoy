package pojo;

/**
 * CourseExp类的描述：
 * 某课程的实验
 * @author 黄金坤（HJK）
 * @since 2021/11/23  17:18
 */

public class CourseExp {
    private String courseID;
    private String expname;
    private int percent;
    private int priority;
    private int difficulty;
    private int status;

    public CourseExp() {
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getExpname() {
        return expname;
    }

    public void setExpname(String expname) {
        this.expname = expname;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CourseExp{" +
                "courseID='" + courseID + '\'' +
                ", expname='" + expname + '\'' +
                ", percent=" + percent +
                ", priority=" + priority +
                ", difficulty=" + difficulty +
                ", status=" + status +
                '}';
    }
}
