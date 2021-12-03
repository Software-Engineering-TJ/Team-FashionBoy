package dao.inter;

import pojo.Experiment;

import javax.servlet.ServletResponse;
import java.util.List;

public interface ExperimentDao {
    int InsertExperiment(String courseID,String expname,String classID,int year,int month,int day,int hour,int minute,String expInfo);
    Experiment QueryExperiment(String courseID, String classID,String expname);
    List<Experiment> QueryExperimentsByCourseIDAndClassID(String courseID,String classID);
    int UpdateExperiment(String courseID, String classID, String expname, String endDate, String expInfo);
}
