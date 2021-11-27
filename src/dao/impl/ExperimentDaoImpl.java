package dao.impl;

import dao.inter.ExperimentDao;
import pojo.Experiment;

import java.util.List;

/**
 * ExperimentDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/11/23  22:24
 */

public class ExperimentDaoImpl extends BaseDao implements ExperimentDao {
    @Override
    public int InsertExperiment(String courseID, String expname, String classID,
                                String startDate,String endDate, String expInfo) {
        String sql = "insert into experiment(`courseID`,`expname`,`classID`," +
                "`startDate`,`endDate`,`expInfo`) values(?,?,?,?,?,?)" ;
        return update(sql,courseID,expname,classID,startDate,endDate,expInfo);
    }

    @Override
    public Experiment QueryExperiment(String courseID, String classID, String expname) {
        String sql = "select * from experiment where courseID = ? and classID = ? and expname = ?";
        return queryForOne(Experiment.class,sql,courseID,classID,expname);
    }

    @Override
    public List<Experiment> QueryExperimentsByCourseIDAndClassID(String courseID, String classID) {
        String sql= "select * from experiment where courseID = ? and classID = ?";
        return queryForList(Experiment.class,sql, courseID,classID);
    }

    @Override
    public int UpdateExperiment(String courseID, String classID, String expname, String endDate, String expInfo) {
        String sql = "update experiment set `endDate` = ? , `expInfo` = ? where(`courseID` = ? and `classID` = ? and `expname` = ?)";
        return update(sql,endDate,expInfo,courseID,classID,expname);
    }
}
