package nl.rmjb.triviabackend.models;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    @Test
    void testQuestionId() {
        Question question = new Question();

        UUID newQuestionId = UUID.randomUUID();
        question.setQuestionId(newQuestionId);

        assertEquals(question.getQuestionId(), newQuestionId);
    }

    @Test
    void testQuestion() {
        Question question = new Question();

        String newQuestion = "What is the capital of France?";
        question.setQuestion(newQuestion);

        assertEquals(question.getQuestion(),newQuestion);
    }

    @Test
    void testAnswerChoices() {
        Question question = new Question();

        List<String> newAnswerChoices = Arrays.asList("Parijs", "Brest", "Parijs");
        question.setAnswerChoices(newAnswerChoices);

        assertEquals(question.getAnswerChoices(),newAnswerChoices);
    }
}
