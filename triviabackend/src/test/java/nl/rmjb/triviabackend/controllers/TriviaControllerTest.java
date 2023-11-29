package nl.rmjb.triviabackend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.rmjb.triviabackend.models.*;
import nl.rmjb.triviabackend.services.TriviaService;
import nl.rmjb.triviabackend.views.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class TriviaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TriviaService triviaService;

    @Test
    void getQuestions() throws Exception {
        // Mock the behavior of triviaService
        List<FormattedQuestion> mockFormattedQuestions = Arrays.asList(
                new FormattedQuestion(UUID.randomUUID(), "Question 1", Arrays.asList("A", "B", "C", "D")),
                new FormattedQuestion(UUID.randomUUID(), "Question 2", Arrays.asList("Z", "X", "C", "V")),
                new FormattedQuestion(UUID.randomUUID(), "Question 3", Arrays.asList("A", "B", "C", "D")),
                new FormattedQuestion(UUID.randomUUID(), "Question 4", Arrays.asList("Z", "X", "C", "V")),
                new FormattedQuestion(UUID.randomUUID(), "Question 5", Arrays.asList("A", "B", "C", "D"))
        );
        Mockito.when(triviaService.getFormattedQuestions(Mockito.anyList())).thenReturn(mockFormattedQuestions);

        // Perform the request and validate the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/trivia/questions"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].question").value("Question 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].question").value("Question 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].question").value("Question 3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[3].question").value("Question 4"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[4].question").value("Question 5"));
    }

    @Test
    void checkAnswersTrue() throws Exception {
        String mockCorrectAnswer = "CorrectAnswer";
        Mockito.when(triviaService.getCorrectAnswer(Mockito.any(), Mockito.any())).thenReturn(mockCorrectAnswer);

        AnswerPayload answerPayload = new AnswerPayload(UUID.randomUUID(), "CorrectAnswer");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/trivia/checkanswers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(answerPayload)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.correct").value(true));
    }

    @Test
    void checkAnswersFalse() throws Exception {
        String mockCorrectAnswer = "CorrectAnswer";
        Mockito.when(triviaService.getCorrectAnswer(Mockito.any(), Mockito.any())).thenReturn(mockCorrectAnswer);

        AnswerPayload answerPayload = new AnswerPayload(UUID.randomUUID(), "SomeAnswer");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/trivia/checkanswers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(answerPayload)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.correct").value(false));
    }
}
