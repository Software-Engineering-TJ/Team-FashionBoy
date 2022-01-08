package pojo;

/**
 * Notice类的描述：
 * 发给班级的通知
 * @author 黄金坤（HJK）
 * @since 2021/11/23  14:06
 */

public class Notice {
    private String courseID;
    private String classID;
    private String date;  //通知时间——精确到秒
    private String content;  //通知内容
    private String instructorNumber;
    private String title;

    public Notice() {
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

    public String getDate() {
        return date;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInstructorNumber() {
        return instructorNumber;
    }

    public void setInstructorNumber(String instructorNumber) {
        this.instructorNumber = instructorNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                ", instructorNumber='" + instructorNumber + '\'' +
                ", title=" + title +
                '}';
    }
}
