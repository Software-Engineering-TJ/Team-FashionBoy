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
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private int percent;

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "Attend{" +
                "courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", percent=" + percent +
                '}';
    }
}
