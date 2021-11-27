package service.inter;

import java.util.List;
import java.util.Map;

public interface InstructorService {
    //获取教师教授的课程
    List<Map<String,String>> GetSections(String instructorNumber);
}
