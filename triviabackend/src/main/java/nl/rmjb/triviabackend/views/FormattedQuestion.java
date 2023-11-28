package nl.rmjb.triviabackend.views;


import java.util.List;
import java.util.UUID;

public class FormattedQuestion {
    private UUID questionId;
    private String question;
    private List<String> answerChoices;

    public FormattedQuestion(UUID questionId, String question, List<String> answerChoices) {
        this.questionId = questionId;
        this.question = question;
        this.answerChoices = answerChoices;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswerChoices() {
        return answerChoices;
    }

    public void setAnswerChoices(List<String> answerChoices) {
        this.answerChoices = answerChoices;
    }
}
