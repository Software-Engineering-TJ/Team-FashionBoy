package service.Impl;

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
    @Override
    public boolean AddStudent(String email, String password) {
        return false;
    }

    @Override
    public boolean DeleteStudent(String email, String password) {
        return false;
    }

    @Override
    public boolean AddInstructor(String email, String password) {
        return false;
    }

    @Override
    public boolean DeleteInstructor(String email, String password) {
        return false;
    }

    @Override
    public List<Takes> SearchCoursesOfStudent(String email) {
        return null;
    }

    @Override
    public List<Teaches> SearchCoursesOfInstructor(String email) {
        return null;
    }

    @Override
    public boolean SetStudentStatus(int status) {
        return false;
    }

    @Override
    public boolean SetInstructorStatus(int status) {
        return false;
    }
}
