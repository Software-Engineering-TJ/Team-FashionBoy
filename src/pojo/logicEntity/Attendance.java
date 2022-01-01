package pojo.logicEntity;

/**
 * @author JiongJiongStrive
 * @version 1.0.0
 * @ClassName Attendance.java
 * @Description TODO
 * @createTime 2021年12月24日 05:34:00
 */
public class Attendance {
    private String attendanceID;
    private String attendanceName;
    private String startTime;
    private String endTime;
    private String status;

    public String getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(String attendanceID) {
        this.attendanceID = attendanceID;
    }

    public String getAttendanceName() {
        return attendanceName;
    }

    public void setAttendanceName(String attendanceName) {
        this.attendanceName = attendanceName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
