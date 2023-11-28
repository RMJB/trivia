package nl.rmjb.triviabackend.views;

import java.util.UUID;

public class AnswerPayload {
    private UUID questionId;
    private String answer;

    public AnswerPayload(UUID questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId(UUID questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
