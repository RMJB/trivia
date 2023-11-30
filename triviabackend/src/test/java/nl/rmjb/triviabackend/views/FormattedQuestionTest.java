package nl.rmjb.triviabackend.views;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FormattedQuestionTest {

    @Test
    void testFormattedQuestionInitialization() {
        UUID questionId = UUID.randomUUID();
        String questionText = "What is the capital of France?";
        List<String> answerChoices = Arrays.asList("Paris", "Brest", "Parijs", "Brussel");

        FormattedQuestion formattedQuestion = new FormattedQuestion(questionId, questionText, answerChoices);

        assertEquals(questionId, formattedQuestion.getQuestionId());
        assertEquals(questionText, formattedQuestion.getQuestion());
        assertEquals(answerChoices, formattedQuestion.getAnswerChoices());
    }

}
