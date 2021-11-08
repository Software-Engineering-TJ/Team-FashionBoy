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
    private int day;
    private int time;
    private int number;
    private int currentNumber;

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

    public int getDay() {return day;}

    public void setDay(int day) {this.day = day;}

    public int getTime() {return time;}

    public void setTime(int time) {this.time = time;}

    public int getNumber() {return number;}

    public void setNumber(int number) {this.number = number;}

    public int getCurrentNumber() {return currentNumber;}

    public void setCurrentNumber(int currentNumber) {this.currentNumber = currentNumber;}

    @Override
    public String toString() {
        return "Section{" +
                "courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", day=" + day +
                ", time=" + time +
                ", number=" + number +
                ", currentNumber=" + currentNumber +
                '}';
    }
}
