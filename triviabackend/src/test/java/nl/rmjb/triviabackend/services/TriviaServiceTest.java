package nl.rmjb.triviabackend.services;

import nl.rmjb.triviabackend.models.*;
import nl.rmjb.triviabackend.views.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
class TriviaServiceTest {

    @Test
    void getFormattedQuestions() {
        List<Question> questions = Arrays.asList(
                new Question("Question 1", "A", Arrays.asList("B", "C")),
                new Question("Question 2", "X", Arrays.asList("Y", "Z"))
        );

        TriviaService triviaService = new TriviaService();
        List<FormattedQuestion> formattedQuestions = triviaService.getFormattedQuestions(questions);

        assertEquals(2, formattedQuestions.size());
        assertEquals("Question 1", formattedQuestions.get(0).getQuestion());
        //Shuffles the answers so have to find a better way to test
        //assertEquals(Arrays.asList("A", "B", "C"), formattedQuestions.get(0).getAnswerChoices());
        assertEquals("Question 2", formattedQuestions.get(1).getQuestion());
        //assertEquals(Arrays.asList("X", "Y", "Z"), formattedQuestions.get(1).getAnswerChoices());
    }

    @Test
    void getCorrectAnswers() {
        List<Question> questions = Arrays.asList(
                new Question("Question 1", "A", Arrays.asList("B", "C")),
                new Question("Question 2", "X", Arrays.asList("Y", "Z"))
        );

        TriviaService triviaService = new TriviaService();
        List<QuestionAnswer> correctAnswers = triviaService.getCorrectAnswers(questions);

        assertEquals(2, correctAnswers.size());
        assertEquals("A", correctAnswers.get(0).getAnswer());
        assertEquals("X", correctAnswers.get(1).getAnswer());
    }

    @Test
    void getCorrectAnswer() {
        UUID questionId = UUID.randomUUID();
        List<QuestionAnswer> questionAnswers = Arrays.asList(
                new QuestionAnswer(UUID.randomUUID(), "A"),
                new QuestionAnswer(questionId, "B"),
                new QuestionAnswer(UUID.randomUUID(), "C")
        );

        TriviaService triviaService = new TriviaService();
        String correctAnswer = triviaService.getCorrectAnswer(questionId, questionAnswers);

        assertEquals("B", correctAnswer);
    }

    @Test
    void getInitialQuestions() {
        QuestionListAPI questionListAPI = new QuestionListAPI();
        QuestionRaw rawQuestion = new QuestionRaw();
        rawQuestion.setQuestion("Question 1");
        rawQuestion.setCorrect_answer("A");
        rawQuestion.setIncorrect_answers(Arrays.asList("A"));
        questionListAPI.setResults(Arrays.asList(
                rawQuestion
        ));

        TriviaService triviaService = new TriviaService();
        List<Question> initialQuestions = triviaService.getInitialQuestions(questionListAPI);

        assertEquals(1, initialQuestions.size());
        assertEquals("Question 1", initialQuestions.get(0).getQuestion());
        //because it suffles
        assertEquals(Arrays.asList("A", "A"), initialQuestions.get(0).getAnswerChoices());
    }
}
