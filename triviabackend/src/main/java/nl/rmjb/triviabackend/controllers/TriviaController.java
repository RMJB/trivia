package nl.rmjb.triviabackend.controllers;

import nl.rmjb.triviabackend.models.*;
import nl.rmjb.triviabackend.views.*;
import nl.rmjb.triviabackend.services.TriviaService;
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
public class TriviaController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TriviaService triviaService;

    private List<QuestionAnswer> questionAnswers;

    private static String url = "https://opentdb.com/api.php?amount=5&type=multiple";

    @GetMapping("/questions")
    public List<FormattedQuestion> getQuestions() throws Exception {
        QuestionListAPI questionListAPI = restTemplate.getForObject(url, QuestionListAPI.class);
        if (questionListAPI == null) {
            throw new NullPointerException("OpemTDB did not return questions");
        }

        if (questionListAPI.getResponseCode() != 0) {
            checkResponseCode(questionListAPI.getResponseCode());
        }

        List<Question> initialQuestions = triviaService.getInitialQuestions(questionListAPI);

        questionAnswers = triviaService.getCorrectAnswers(initialQuestions);

        return triviaService.getFormattedQuestions(initialQuestions);
    }

    private void checkResponseCode(int responseCode) throws Exception {
        switch (responseCode) {
            case 1:
                throw new Exception("Code 1: No Results Could not return results. The API doesn't have enough questions for your query. (Ex. Asking for 50 Questions in a Category that only has 20.)");
            case 2:
                throw new Exception("Code 2: Invalid Parameter Contains an invalid parameter. Arguements passed in aren't valid. (Ex. Amount = Five)");
            case 3:
                throw new Exception("Code 3: Token Not Found Session Token does not exist.");
            case 4:
                throw new Exception("Code 4: Token Empty Session Token has returned all possible questions for the specified query. Resetting the Token is necessary.");
            case 5:
                throw new Exception("Code 5: Rate Limit Too many requests have occurred. Each IP can only access the API once every 5 seconds.");
        }
    }

    @PostMapping("/checkanswers")
    public ResponseEntity<Map<String, Object>> getQuestionIdAnswerResponse(@RequestBody AnswerPayload payload) throws Exception {
        UUID questionId = payload.getQuestionId();
        String answer = payload.getAnswer();

        System.out.println("Answer received for question " + questionId + ": " + answer);

        String correctAnswer = questionAnswers.stream()
                .filter(questionAnswer -> questionId.equals(questionAnswer.getQuestionId()))
                .findFirst()
                .orElseThrow(Exception::new)
                .getAnswer();

        Map<String, Object> response = new HashMap<>();
        response.put("correct", answer.equals(correctAnswer));
        //response.put("correctAnswer", correctAnswer);

        return ResponseEntity.ok(response);
    }
}