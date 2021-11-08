package dao.inter;

import pojo.Section;

public interface SectionDao {
    Section QuerySectionByCourseIDAndClassID(String courseID,String classID);
}
