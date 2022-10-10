package pl.edu.pb.quizv2;

public class Question {
    private int questionId;
    private boolean trueAnswer;

    public Question(int questionId, boolean trueAnswer){
        this.questionId = questionId;
        this.trueAnswer = trueAnswer;
    }

    public int getQuestionId() {
        return questionId;
    }

    public boolean isTrueAnswer() {
        if (this.trueAnswer == true) {
            return true;
        } else {
            return false;
        }
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setTrueAnswer(boolean trueAnswer) {
        this.trueAnswer = trueAnswer;
    }


}
