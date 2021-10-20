package pojo;

/**
 * classroom类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/18  14:42
 */

public class Classroom {
    private String classID;
    private String classname;
    private int day;
    private int time;
    private int number;

    public Classroom(){}

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "classID='" + classID + '\'' +
                ", classname='" + classname + '\'' +
                ", day=" + day +
                ", time=" + time +
                ", number=" + number +
                '}';
    }
}
