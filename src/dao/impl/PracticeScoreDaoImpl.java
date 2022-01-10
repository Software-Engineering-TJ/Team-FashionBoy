package dao.impl;

import dao.inter.PracticeScoreDao;
import pojo.PracticeScore;

import java.util.List;

public class PracticeScoreDaoImpl extends BaseDao implements PracticeScoreDao {
    @Override
    public List<PracticeScore> QueryPracticeScoreByGroup(String courseID,String classID,String practiceName,int groupNumber) {
        String sql = "select studentNumber from practicescore where (courseID = ? and classID = ? and practiceName = ? and groupNumber = ?) order by individualScore DESC,individualTime ASC";
        return queryForList(PracticeScore.class,sql,courseID,classID,practiceName,groupNumber);
    }

    @Override
    public PracticeScore QueryPracticeScoreByCourseIDAndClassIDAndPracticeNameAndStudentNumber(String courseID, String classID, String practiceName, String studentNumber) {
        String sql = "select * from practicescore where (courseID = ? and classID = ? and practiceName = ? and studentNumber = ?)";
        return queryForOne(PracticeScore.class,sql,courseID,classID,practiceName,studentNumber);
    }

    @Override
    public int insertPracticeScore(String courseID, String classID, String practiceName, String studentNumber, double score, String time, int groupNumber) {
        String sql = "insert into practicescore(`courseID`,`classID`,`practiceName`,`studentNumber`,`individualScore`,`individualTime`,`groupNumber`)" +
                " values (?,?,?,?,?,?,?)";
        return update(sql,courseID,classID,practiceName,studentNumber,score,time,groupNumber);
    }
}
