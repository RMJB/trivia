package nl.rmjb.triviabackend.services;

import nl.rmjb.triviabackend.models.Question;
import nl.rmjb.triviabackend.models.TriviaApiResponse;
import nl.rmjb.triviabackend.models.TriviaQuestion;
import nl.rmjb.triviabackend.views.AnswerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuestions() {
        TriviaApiResponse mockApiResponse = new TriviaApiResponse();
        mockApiResponse.setResponse_code(0);

        TriviaQuestion triviaQuestion = new TriviaQuestion();

        triviaQuestion.setCategory("General Knowledge");
        triviaQuestion.setType("multiple");
        triviaQuestion.setDifficulty("easy");
        triviaQuestion.setQuestion("What is the capital of France?");
        triviaQuestion.setCorrect_answer("Paris");
        triviaQuestion.setIncorrect_answers(Arrays.asList("Paradijs", "Brest", "Parijs"));

        mockApiResponse.setResults(List.of(triviaQuestion));

        when(restTemplate.getForObject(anyString(), eq(TriviaApiResponse.class))).thenReturn(mockApiResponse);

        List<Question> questions = questionService.getQuestions();

        assertNotNull(questions);
        assertFalse(questions.isEmpty());
        Question question = questions.get(0);
        assertEquals(question.getQuestion(), "What is the capital of France?");
        assertNotNull(question.getAnswerChoices());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(TriviaApiResponse.class));
    }

    @Test
    void testGetQuestionsApiError() {
        TriviaApiResponse mockApiResponse = new TriviaApiResponse();
        mockApiResponse.setResponse_code(1);

        when(restTemplate.getForObject(anyString(), eq(TriviaApiResponse.class))).thenReturn(mockApiResponse);

        List<Question> questions = questionService.getQuestions();

        assertTrue(questions.isEmpty());
        verify(restTemplate, times(1)).getForObject(anyString(), eq(TriviaApiResponse.class));
    }

    @Test
    void testCheckAnswer() {
        TriviaApiResponse mockApiResponse = new TriviaApiResponse();
        mockApiResponse.setResponse_code(0);

        TriviaQuestion triviaQuestion = new TriviaQuestion();

        triviaQuestion.setCategory("General Knowledge");
        triviaQuestion.setType("multiple");
        triviaQuestion.setDifficulty("easy");
        triviaQuestion.setQuestion("What is the capital of France?");
        triviaQuestion.setCorrect_answer("Paris");
        triviaQuestion.setIncorrect_answers(Arrays.asList("Paradijs", "Brest", "Parijs"));

        mockApiResponse.setResults(List.of(triviaQuestion));

        when(restTemplate.getForObject(anyString(), eq(TriviaApiResponse.class))).thenReturn(mockApiResponse);

        List<Question> questions = questionService.getQuestions();
        Question question = questions.get(0);
        UUID uuid = question.getQuestionId();

        AnswerRequest answerRequest = new AnswerRequest(uuid, "Paris");

        assertTrue(questionService.checkAnswer(answerRequest));
    }
}

