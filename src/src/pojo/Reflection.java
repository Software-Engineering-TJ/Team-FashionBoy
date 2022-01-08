package pojo;

/**
 * Reflection类的描述：
 * 学生对课程的反馈
 * @author 黄金坤（HJK）
 * @since 2021/11/23  17:11
 */

public class Reflection {
    private String courseID;
    private String classID;
    private String studentNumber;
    private String date;  //反馈时间——精确到秒
    private String content;  //反馈内容

    public String getCourseID() {
        return courseID;
    }

    public String getClassID() {
        return classID;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Reflection{" +
                "courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", studentNumber='" + studentNumber + '\'' +
                ", date='" + date + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
