package dao.impl;

import dao.inter.ChoiceQuestionDao;
//import org.junit.Test;
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
        String sql = "select * from choicequestion where choiceId = ?";
        return queryForOne(ChoiceQuestion.class, sql, id);
    }

    @Override
    public int getCount() {
        String sql = "select count(*) as count from choicequestion";
        Counter c =  queryForOne(Counter.class, sql);
        return c.getCount();
    }

    @Override
    public int addQuestion(String choiceQuestion, String choiceOption, int choiceDifficulty, String choiceAnswer, String choiceAnalysis, double choiceScore) {
        String sql = "insert into choicequestion(choiceQuestion,choiceOption,choiceDifficulty,choiceAnswer,choiceAnalysis,choiceScore) value(?,?,?,?,?,?)";
        return update(sql, choiceQuestion, choiceOption,  choiceDifficulty, choiceAnswer, choiceAnalysis, choiceScore);
    }

    @Override
    public List<ChoiceQuestion> getAllQuestions() {
        String sql = "select * from choicequestion";
        return queryForList(ChoiceQuestion.class,sql);
    }
}
