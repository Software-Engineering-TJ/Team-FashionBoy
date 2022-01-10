package dao.inter;

import pojo.ChoiceQuestion;

import java.util.List;

public interface ChoiceQuestionDao {
    ChoiceQuestion getQuestionByQuestionId(int id);
    int getCount();
    int addQuestion(String choiceQuestion,String choiceOption,int choiceDifficulty,String choiceAnswer,String choiceAnalysis,double choiceScore);
    List<ChoiceQuestion> getAllQuestions();
}
