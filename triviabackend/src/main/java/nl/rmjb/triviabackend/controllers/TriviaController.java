package nl.rmjb.triviabackend.controllers;

import nl.rmjb.triviabackend.models.Question;
import nl.rmjb.triviabackend.services.QuestionService;
import nl.rmjb.triviabackend.views.AnswerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/trivia")
public class TriviaController {
    private final QuestionService questionService;

    @Autowired
    public TriviaController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public List<Question> getQuestions() {
        return questionService.getQuestions();
    }

    @PostMapping("/checkanswers")
    public boolean checkAnswer(@RequestBody AnswerRequest answerRequest) {
        return questionService.checkAnswer(answerRequest);
    }
}

