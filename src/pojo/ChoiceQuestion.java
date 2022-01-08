package pojo;

public class ChoiceQuestion {
    private int choice_id;
    private String choice_question;
    private String choice_option;
    private int choice_difficulty;
    private String choice_answer;
    private String choice_analysis;
    private double choice_score;

    public int getChoice_id() {
        return choice_id;
    }

    public void setChoice_id(int choice_id) {
        this.choice_id = choice_id;
    }

    public String getChoice_question() {
        return choice_question;
    }

    public void setChoice_question(String choice_question) {
        this.choice_question = choice_question;
    }

    public String getChoice_option() {
        return choice_option;
    }

    public void setChoice_option(String choice_option) {
        this.choice_option = choice_option;
    }

    public int getChoice_difficulty() {
        return choice_difficulty;
    }

    public void setChoice_difficulty(int choice_difficulty) {
        this.choice_difficulty = choice_difficulty;
    }

    public String getChoice_answer() {
        return choice_answer;
    }

    public void setChoice_answer(String choice_answer) {
        this.choice_answer = choice_answer;
    }

    public String getChoice_analysis() {
        return choice_analysis;
    }

    public void setChoice_analysis(String choice_analysis) {
        this.choice_analysis = choice_analysis;
    }

    public double getChoice_score() {
        return choice_score;
    }

    public void setChoice_score(double choice_score) {
        this.choice_score = choice_score;
    }

    @Override
    public String toString() {
        return '{' +
                "\"choiceID\":" + choice_id +
                ", \"choiceQuestion\":" + '\"' + choice_question + '\"' +
                ", \"choiceOption\":" + '\"' + choice_option + '\"' +
                ", \"choiceDifficulty\":" + choice_difficulty +
                ", \"choiceAnswer\":" + '\"' + choice_answer + '\"' +
                ", \"choiceAnalysis\":" + '\"' + choice_analysis + '\"' +
                ", \"choiceScore\":" + choice_score +
                '}';
    }
}
