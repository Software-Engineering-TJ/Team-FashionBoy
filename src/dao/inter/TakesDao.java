package dao.inter;

import pojo.Takes;

import java.util.List;

public interface TakesDao {
    //根据学生number查询所有相关课程
    List<Takes> QueryTakesByStudentNumber(String studentNumber);
}
