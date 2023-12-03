package nl.rmjb.triviabackend.models;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TriviaQuestionTest {

    @Test
    void testGetterAndSetter() {
        TriviaQuestion triviaQuestion = new TriviaQuestion();

        triviaQuestion.setCategory("General Knowledge");
        triviaQuestion.setType("multiple");
        triviaQuestion.setDifficulty("easy");
        triviaQuestion.setQuestion("What is the capital of France?");
        triviaQuestion.setCorrect_answer("Paris");
        triviaQuestion.setIncorrect_answers(Arrays.asList("Paradijs", "Brest", "Parijs"));

        assertEquals("General Knowledge", triviaQuestion.getCategory());
        assertEquals("multiple", triviaQuestion.getType());
        assertEquals("easy", triviaQuestion.getDifficulty());
        assertEquals("What is the capital of France?", triviaQuestion.getQuestion());
        assertEquals("Paris", triviaQuestion.getCorrect_answer());
        List<String> incorrectAnswers = Arrays.asList("Paradijs", "Brest", "Parijs");
        assertEquals(incorrectAnswers, triviaQuestion.getIncorrect_answers());
    }
}
