package service.Impl;

import com.google.gson.reflect.TypeToken;
import dao.impl.CourseDaoImpl;
import dao.impl.TeachesDaoImpl;
import dao.inter.CourseDao;
import dao.inter.TeachesDao;
import pojo.Course;
import pojo.Teaches;
import service.inter.InstructorService;
import utils.RequestJsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * InstructorServiceImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/11/27  22:38
 */

public class InstructorServiceImpl implements InstructorService {

    private TeachesDao teachesDao = new TeachesDaoImpl();
    private CourseDao courseDao = new CourseDaoImpl();

    @Override
    public List<Map<String, String>> GetSections(String instructorNumber) {
        //创建存储sections信息的列表
        List<Map<String,String>> sectionInfoList = new ArrayList<>();
        List<Teaches> teachesList = teachesDao.QueryTeachesByInstructorNumber(instructorNumber);

        for(Teaches t : teachesList){
            Map<String,String> sectionInfo = new HashMap<>();
            //获取courseID和classID
            sectionInfo.put("courseID",t.getCourseID());
            sectionInfo.put("classID",t.getClassID());
            //获取课程名
            Course course = courseDao.QueryCourseByCourseID(t.getCourseID());
            sectionInfo.put("title",course.getTitle());
            //获取duty信息
            String duty = "教师";
            if(course.getInstructorNumber().equals(instructorNumber)){
                duty = duty + ",责任教师";
            }
            sectionInfo.put("duty",duty);

            sectionInfoList.add(sectionInfo);
        }

        return sectionInfoList;
    }
}
