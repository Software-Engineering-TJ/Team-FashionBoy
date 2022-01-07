package dao.impl;

import dao.inter.ExpScoreDao;
import pojo.ExpScore;

import java.util.List;

/**
 * ExpScoreDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/11/23  22:38
 */

public class ExpScoreDaoImpl extends BaseDao implements ExpScoreDao {
    @Override
    public int InsertExpScore(String studentNumber, String courseID, String expname, String classID,String fileUrl) {
        String sql = "insert into expscore(`studentNumber`,`courseID`,`expname`,`classID`,`fileUrl`) values(?,?,?,?,?)";
        return update(sql,studentNumber,courseID,expname,classID,fileUrl);
    }

    @Override
    public ExpScore QueryExpScoreByCourseIDAndClassIDAndExpnameAndStudentNumber(String courseID, String classID, String expname, String studentNumber) {
        String sql = "select * from expscore where courseID = ? and classID = ? and expname = ? and studentNumber = ?";
        return queryForOne(ExpScore.class,sql,courseID,classID ,expname,studentNumber);
    }

    @Override
    public List<ExpScore> QueryExpScoresByExperiment(String courseID, String expname, String classID) {
        String sql = "select * from expscore where courseID = ? and expname = ? and classID = ?";
        return queryForList(ExpScore.class,sql,courseID,expname,classID);
    }

    @Override
    public int UpdateExpScore(String studentNumber, String courseID, String expname, String classID,
                              float score, String comment) {
        String sql = "update `expscore` set `score` = ?,`comment` = ? where (`studentNumber` = ? and " +
                "`courseID` = ? and `expname` = ? and `classID` = ?)";
        return update(sql,score,comment,studentNumber,courseID,expname,classID);
    }

    @Override
    public int UpdateFileUrl(String courseID, String classID, String expname, String studentNumber, String fileUrl) {
        String sql = "update `expscore` set `fileUrl` = ? where (`courseID` = ? and " +
                "`classID` = ? and `expname` = ? and `studentNumber` = ?)";
        return update(sql,fileUrl,courseID,classID,expname,studentNumber);
    }

    @Override
    public int DeleteByFileUrl(String fileUrl) {
        String sql = "delete from expscore where fileUrl = ?";
        return update(sql,fileUrl);
    }

    @Override
    public List<ExpScore> QueryExpScoresByExperimentAndScoreDESC(String courseID, String classID, String expname) {
        String sql = "select * from expscore where (courseID = ? and classID = ? and expname = ?) order by score DESC";
        return queryForList(ExpScore.class,sql,courseID,classID,expname);
    }

}
