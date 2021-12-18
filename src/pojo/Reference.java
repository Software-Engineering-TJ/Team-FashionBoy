package pojo;

public class Reference {
    private int id;
    private String courseID;
    private String classID;
    private String instructorNumber;
    private String fileUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getInstructorNumber() {
        return instructorNumber;
    }

    public void setInstructorNumber(String instructorNumber) {
        this.instructorNumber = instructorNumber;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "id=" + id +
                ", courseID='" + courseID + '\'' +
                ", classID='" + classID + '\'' +
                ", instructorNumber='" + instructorNumber + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }
}
