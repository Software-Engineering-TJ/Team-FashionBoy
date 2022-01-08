package dao.inter;

import pojo.ChoiceQuestion;

import java.util.List;

public interface ChoiceQuestionDao {
    ChoiceQuestion getQuestionByQuestionId(int id);
    int getCount();
}
