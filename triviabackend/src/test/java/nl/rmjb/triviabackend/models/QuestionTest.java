package nl.rmjb.triviabackend.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class QuestionTest {

    @Test
    void testQuestionInitialization() {
        String questionText = "What is the capital of France?";
        String correctAnswer = "Paris";
        List<String> incorrectAnswers = Arrays.asList("Brest", "Parijs", "Brussel");

        Question question = new Question(questionText, correctAnswer, incorrectAnswers);

        assertNotNull(question.getQuestionId());
        assertEquals(questionText, question.getQuestion());
        assertEquals(correctAnswer, question.getAnswer());
        assertEquals(incorrectAnswers, question.getIncorrectAnswers());
        assertNotNull(question.getAnswerChoices());
        assertEquals(4, question.getAnswerChoices().size());

        assertTrue(question.getAnswerChoices().contains(correctAnswer));
    }

}
