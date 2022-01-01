package pojo;

public class Counter {
    private int courseId;

    private int classId;

    public Counter() {
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "courseId=" + courseId +
                ", classId=" + classId +
                '}';
    }
}
