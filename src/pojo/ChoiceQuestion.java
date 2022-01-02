package pojo;

public class ChoiceQuestion {
    private int choiceID;
    private String choiceQuestion;
    private String choiceOption;
    private int choiceDifficulty;
    private String choiceAnswer;
    private String choiceAnalysis;
    private double choiceScore;

    public int getChoiceID() {
        return choiceID;
    }

    public void setChoiceID(int choiceID) {
        this.choiceID = choiceID;
    }

    public String getChoiceQuestion() {
        return choiceQuestion;
    }

    public void setChoiceQuestion(String choiceQuestion) {
        this.choiceQuestion = choiceQuestion;
    }

    public String getChoiceOption() {
        return choiceOption;
    }

    public void setChoiceOption(String choiceOption) {
        this.choiceOption = choiceOption;
    }

    public int getChoiceDifficulty() {
        return choiceDifficulty;
    }

    public void setChoiceDifficulty(int choiceDifficulty) {
        this.choiceDifficulty = choiceDifficulty;
    }

    public String getChoiceAnswer() {
        return choiceAnswer;
    }

    public void setChoiceAnswer(String choiceAnswer) {
        this.choiceAnswer = choiceAnswer;
    }

    public String getChoiceAnalysis() {
        return choiceAnalysis;
    }

    public void setChoiceAnalysis(String choiceAnalysis) {
        this.choiceAnalysis = choiceAnalysis;
    }

    public double getChoiceScore() {
        return choiceScore;
    }

    public void setChoiceScore(double choiceScore) {
        this.choiceScore = choiceScore;
    }

    @Override
    public String toString() {
        return "ChoiceQuestion{" +
                "choiceID=" + choiceID +
                ", choiceQuestion='" + choiceQuestion + '\'' +
                ", choiceOption='" + choiceOption + '\'' +
                ", choiceDifficulty=" + choiceDifficulty +
                ", choiceAnswer='" + choiceAnswer + '\'' +
                ", choiceAnalysis='" + choiceAnalysis + '\'' +
                ", choiceScore=" + choiceScore +
                '}';
    }
}
