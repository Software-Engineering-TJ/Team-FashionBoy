package dao.impl;

import dao.inter.ChoiceQuestionDao;
import org.junit.Test;
import pojo.ChoiceQuestion;

import pojo.Course;
import pojo.ExpReport;
import pojo.logicEntity.Counter;
import utils.RandomHandler;
import java.util.HashSet;
import java.util.List;

public class ChoiceQuestionDaoImpl extends BaseDao implements ChoiceQuestionDao {

    @Override
    public ChoiceQuestion getQuestionByQuestionId(int id) {
        String sql = "select * from choicequestion where choice_id = ?";
        return queryForOne(ChoiceQuestion.class, sql, id);
    }

    @Override
    public int getCount() {
        String sql = "select count(*) as count from choicequestion";
        Counter c =  queryForOne(Counter.class, sql);
        return c.getCount();
    }

}
