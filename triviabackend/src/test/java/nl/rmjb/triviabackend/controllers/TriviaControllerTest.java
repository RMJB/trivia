package nl.rmjb.triviabackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.rmjb.triviabackend.models.Question;
import nl.rmjb.triviabackend.services.QuestionService;
import nl.rmjb.triviabackend.views.AnswerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TriviaControllerTest {

    private MockMvc mockMvc;
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = mock(QuestionService.class);
        TriviaController triviaController = new TriviaController(questionService);
        mockMvc = MockMvcBuilders.standaloneSetup(triviaController).build();
    }

    @Test
    void testGetQuestions() throws Exception {
        List<Question> mockQuestions = Arrays.asList(new Question(), new Question());
        when(questionService.getQuestions()).thenReturn(mockQuestions);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/trivia/questions")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status, "Incorrect Response Status");

        String content = result.getResponse().getContentAsString();
        List<Question> questions = Arrays.asList(new ObjectMapper().readValue(content, Question[].class));

        assertEquals(mockQuestions.size(), questions.size(), "Incorrect number of questions");
    }

    @Test
    void testCheckAnswer() throws Exception {
        AnswerRequest answerRequest = new AnswerRequest(UUID.randomUUID(), "test answer");
        when(questionService.checkAnswer(answerRequest)).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/trivia/checkanswers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(answerRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        int status = result.getResponse().getStatus();
        assertEquals(200, status, "Incorrect Response Status");

        String content = result.getResponse().getContentAsString();
        boolean responseValue = new ObjectMapper().readValue(content, Boolean.class);

        assertTrue(responseValue, "Expected the answer to be true");
    }
}
