package pojo;

public class Counter {
    private int courseID;

    private int classID;

    public Counter() {
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "courseID=" + courseID +
                ", classID=" + classID +
                '}';
    }
}
