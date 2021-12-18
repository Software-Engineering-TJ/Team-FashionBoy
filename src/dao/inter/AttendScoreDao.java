package dao.inter;

public interface AttendScoreDao {
    int InsertAttendScore(String courseID,String classID,String title,String studentNumber,int onTime);
}
