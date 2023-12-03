package nl.rmjb.triviabackend.views;

import lombok.Data;

import java.util.UUID;

@Data
public class AnswerRequest {
    private UUID questionId;
    private String answer;

    public AnswerRequest(UUID questionId, String answer) {
        this.questionId = questionId;
        this.answer = answer;
    }
}

