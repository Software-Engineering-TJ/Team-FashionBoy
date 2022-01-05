package pojo.logicEntity;

public class ViewExperimentInfo {
    private String expName;
    private String startDate;
    private String endDate;
    private String expInfo;
    private int priority;
    private int difficulty;
    private int weight;

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getExpInfo() {
        return expInfo;
    }

    public void setExpInfo(String expInfo) {
        this.expInfo = expInfo;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "ViewExperimentInfo{" +
                "expName='" + expName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", expInfo='" + expInfo + '\'' +
                ", priority=" + priority +
                ", difficulty=" + difficulty +
                ", weight=" + weight +
                '}';
    }
}
