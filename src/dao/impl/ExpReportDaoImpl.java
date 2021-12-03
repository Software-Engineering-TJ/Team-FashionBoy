package dao.impl;

import dao.inter.ExpReportDao;
import pojo.ExpReport;

import java.util.List;

/**
 * ExpReportDaoImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/11/23  22:01
 */

public class ExpReportDaoImpl extends BaseDao implements ExpReportDao {
    @Override
    public int InsertExpReport(String courseID, String expname, String classID,String reportName,
                               String endDate, String reportInfo, String fileType,String startDate) {
        String sql = "insert into expreport(`courseID`,`expname`,`classID`,`reportName`," +
                "`endDate`,`reportInfo`,`fileType`,`startDate`) values(?,?,?,?,?,?,?,?)";
        return update(sql,courseID,expname,classID,reportName,endDate,reportInfo,fileType,startDate);
    }

    @Override
    public List<ExpReport> QueryExpReportsByCourseIDAndClassID(String courseID, String classID) {
        String sql = "select * from expreport where courseID = ? and classID = ?";
        return queryForList(ExpReport.class,sql,courseID,classID);
    }

    @Override
    public int DeleteExpReport(String courseID, String classID, String expname, String reportName) {
        String sql = "delete from expreport where(`courseID` = ? and `classID` = ? and `expname` = ? and `reportName` = ?)";
        return update(sql,courseID,classID,expname,reportName);
    }

    @Override
    public int UpdateReportDesc(String courseID, String classID, String expname, String reportName, String reportInfo, String endDate, String fileType) {
        String sql = "update `expreport` set `reportName` = ? , `reportInfo` = ? , `endDate` = ? , `fileType` = ? " +
                "where(`courseID` = ? and `classID` = ? and `expname` = ?)";
        return update(sql,reportName,reportInfo,endDate,fileType,courseID,classID,expname);
    }
}
