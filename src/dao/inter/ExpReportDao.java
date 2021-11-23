package dao.inter;

import pojo.ExpReport;
import java.util.List;

public interface ExpReportDao {
    int InsertExpReport(String courseID,String expname,String classID,int year,int month,
                        int day,int hour,int minute,String reportInfo,String fileType);
    List<ExpReport> QueryExpReportsByCourseIDAndClassID(String courseID,String classID);
}
