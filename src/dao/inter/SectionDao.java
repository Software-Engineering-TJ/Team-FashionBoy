package dao.inter;

import pojo.Section;

import java.util.List;

public interface SectionDao {
    Section QuerySectionByCourseIDAndClassID(String courseID,String classID);
    List<Section> QuerySectionByCourseID(String courseID);
    int insertSection(String courseID,String classID, int day, int time, int number);
   int deleteSection(String courseID, String classID);
}
