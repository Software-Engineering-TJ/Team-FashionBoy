package pojo;

/**
 * ExpReport类的描述：
 * 实验报告
 * @author 黄金坤（HJK）
 * @since 2021/11/23  17:05
 */

public class ExpReport {
    private String courseID;
    private String expname;
    private String classID;
    private String reportName;
    private String reportInfo;
    private String fileType;
    private String startDate;

    public ExpReport() {
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

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportInfo() {
        return reportInfo;
    }

    public void setReportInfo(String reportInfo) {
        this.reportInfo = reportInfo;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "ExpReport{" +
                "courseID='" + courseID + '\'' +
                ", expname='" + expname + '\'' +
                ", classID='" + classID + '\'' +
                ", reportName='" + reportName + '\'' +
                ", reportInfo='" + reportInfo + '\'' +
                ", fileType='" + fileType + '\'' +
                ", startDate='" + startDate + '\'' +
                '}';
    }
}
