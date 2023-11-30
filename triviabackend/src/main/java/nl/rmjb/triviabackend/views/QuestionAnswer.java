package nl.rmjb.triviabackend.views;

import java.util.UUID;

public class QuestionAnswer {
    private final UUID questionId;
    private final String answer;

    public QuestionAnswer(UUID questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }

    public UUID getQuestionId() {
        return questionId;
    }

    public String getAnswer() {
        return answer;
    }


}