package dao.inter;

import pojo.Section;

public interface SectionDao {
    Section QuerySectionBySectionID(String courseID,String classID);
}
