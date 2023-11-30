package nl.rmjb.triviabackend.controllers;

import nl.rmjb.triviabackend.models.Question;
import nl.rmjb.triviabackend.models.QuestionListAPI;
import nl.rmjb.triviabackend.services.TriviaService;
import nl.rmjb.triviabackend.views.AnswerPayload;
import nl.rmjb.triviabackend.views.FormattedQuestion;
import nl.rmjb.triviabackend.views.QuestionAnswer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/trivia")
public class TriviaController {

    private final RestTemplate restTemplate;
    private final TriviaService triviaService;
    private List<QuestionAnswer> questionAnswers;

    private static final String API_URL = "https://opentdb.com/api.php?amount=5&type=multiple";

    @Autowired
    public TriviaController(RestTemplate restTemplate, TriviaService triviaService) {
        this.restTemplate = restTemplate;
        this.triviaService = triviaService;
    }

    @GetMapping("/questions")
    public List<FormattedQuestion> getQuestions() throws Exception {
        QuestionListAPI questionListAPI = restTemplate.getForObject(API_URL, QuestionListAPI.class);
        if (questionListAPI == null) {
            throw new NullPointerException("OpenTDB did not return questions");
        }

        checkResponseCode(questionListAPI.getResponseCode());

        List<Question> initialQuestions = triviaService.getInitialQuestions(questionListAPI);
        questionAnswers = triviaService.getCorrectAnswers(initialQuestions);

        return triviaService.getFormattedQuestions(initialQuestions);
    }

    private void checkResponseCode(int responseCode) throws Exception {
        switch (responseCode) {
            case 1:
                throw new Exception("Code 1: No Results - The API doesn't have enough questions for your query.");
            case 2:
                throw new Exception("Code 2: Invalid Parameter - Contains an invalid parameter.");
            case 3:
                throw new Exception("Code 3: Token Not Found - Session Token does not exist.");
            case 4:
                throw new Exception("Code 4: Token Empty - Session Token has returned all possible questions for the specified query.");
            case 5:
                throw new Exception("Code 5: Rate Limit - Too many requests have occurred. Each IP can only access the API once every 5 seconds.");
        }
    }

    @PostMapping("/checkanswers")
    public ResponseEntity<Map<String, Object>> checkAnswers(@RequestBody AnswerPayload payload) throws Exception {
        UUID questionId = payload.getQuestionId();
        String answer = payload.getAnswer();

        System.out.println("Answer received for question " + questionId + ": " + answer);

        String correctAnswer = triviaService.getCorrectAnswer(questionId, questionAnswers);

        Map<String, Object> response = new HashMap<>();
        response.put("correct", answer.equals(correctAnswer));

        return ResponseEntity.ok(response);
    }
}
