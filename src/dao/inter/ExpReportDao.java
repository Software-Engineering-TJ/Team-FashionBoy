package dao.inter;

import pojo.ExpReport;
import java.util.List;

public interface ExpReportDao {
    int InsertExpReport(String courseID,String expname,String classID,String reportName,
                        String endDate,String reportInfo,String fileType,String startDate);
    List<ExpReport> QueryExpReportsByCourseIDAndClassID(String courseID,String classID);
    int DeleteExpReport(String courseID, String classID, String expname, String reportName);
}
