package service.Impl;

import dao.InstructorDao;
import dao.StudentDao;
import dao.impl.InstructorDaoImpl;
import dao.impl.StudentDaoImpl;
import pojo.Takes;
import pojo.Teaches;
import service.AdministrationService;

import java.util.List;

/**
 * AdministrationServiceImpl类的描述：
 *
 * @author 黄金坤（HJK）
 * @since 2021/10/21  14:08
 */

public class AdministrationServiceImpl implements AdministrationService {

    private StudentDao studentDao = new StudentDaoImpl();
    private InstructorDao instructorDao = new InstructorDaoImpl();
    //后续肯定还需要takes、teaches


    @Override
    public boolean AddStudent(String email, String password) {
        return false;
    }

    @Override
    public boolean DeleteStudent(String email) {
        return false;
    }

    @Override
    public boolean AddInstructor(String email, String password) {
        return false;
    }

    @Override
    public boolean DeleteInstructor(String email) {
        return false;
    }

    @Override
    public List<Takes> SearchTakesOfStudent(String email) {
        return null;
    }

    @Override
    public List<Teaches> SearchTeachesOfInstructor(String email) {
        return null;
    }

    @Override
    public boolean SetStudentStatus(String email, String courseID, String classID, int status) {
        return false;
    }

    @Override
    public boolean SetInstructorStatus(String email, String courseID, String classID, int status) {
        return false;
    }
}
