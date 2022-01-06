package dao.inter;

import pojo.AttendScore;

import java.util.List;

public interface AttendScoreDao {
    int InsertAttendScore(String courseID,String classID,String title,String studentNumber,int onTime);

    List<AttendScore> getAttendScoreByCourseIDAndClassIDAndTitle(String courseID, String classID, String title);

    List<AttendScore> getAttendScoreByCourseIDAndClassIDAndStudentNumber(String courseID, String classID, String studentNumber);

    AttendScore getAttendScoreByCourseIDAndClassIDAndTitleAndStudentNumber(String courseID, String classID, String title, String studentNumber);
}
