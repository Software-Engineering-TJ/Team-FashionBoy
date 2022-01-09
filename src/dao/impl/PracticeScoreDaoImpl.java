package dao.impl;

import dao.inter.PracticeScoreDao;
import pojo.PracticeScore;

import java.util.List;

public class PracticeScoreDaoImpl extends BaseDao implements PracticeScoreDao {
    @Override
    public List<PracticeScore> QueryPracticeScoreByGroup(String courseID, String classID, String practiceName) {
        String sql = "select groupNumber from practicescore where (courseID = ? and classID = ? and practiceName = ?) group by groupNumber order by groupScore DESC,finishTime ASC";
        return queryForList(PracticeScore.class,sql,courseID,classID,practiceName);
    }

    @Override
    public PracticeScore QueryPracticeScoreByCourseIDAndClassIDAndPracticeNameAndStudentNumber(String courseID, String classID, String practiceName, String studentNumber) {
        String sql = "select * from practicescore where (courseID = ? and classID = ? and practiceName = ? and studentNumber = ?)";
        return queryForOne(PracticeScore.class,sql,courseID,classID,practiceName,studentNumber);
    }
}
