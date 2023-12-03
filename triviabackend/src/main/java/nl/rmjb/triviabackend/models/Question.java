package nl.rmjb.triviabackend.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID questionId;
    private String question;
    private List<String> answerChoices;


    public UUID getQuestionId() {
        return questionId;
    }

    public void setQuestionId() {
        this.questionId = UUID.randomUUID();
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
