package nl.rmjb.triviabackend.services;

import nl.rmjb.triviabackend.models.Question;
import nl.rmjb.triviabackend.models.TriviaApiResponse;
import nl.rmjb.triviabackend.models.TriviaQuestion;
import nl.rmjb.triviabackend.views.AnswerRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.text.StringEscapeUtils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private static final String TRIVIA_API_URL = "https://opentdb.com/api.php?amount=5&type=multiple";
    private final List<AnswerRequest> answers = new ArrayList<>();
    private RestTemplate restTemplate;

    public List<Question> getQuestions() {
        try {
            // Get new questions every time
            TriviaApiResponse response = restTemplate.getForObject(TRIVIA_API_URL, TriviaApiResponse.class);

            // Check for API-defined error codes
            if (response != null && response.getResponse_code() != 0) {
                handleApiError(response.getResponse_code());
            }

            // Return masked questions
            if (response != null && response.getResults() != null) {
                return response.getResults().stream()
                        .map(this::transformTriviaQuestion)
                        .collect(Collectors.toList());
            }
            // Return an empty list instead of just null
            return Collections.emptyList();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return Collections.emptyList();
    }

    public Boolean checkAnswer(AnswerRequest answerRequest){
        //System.out.println("Answer received for question " + answerRequest.getQuestionId() + ": " + answerRequest.getAnswer() );
        return answers.contains(answerRequest);
    }

    private Question transformTriviaQuestion(TriviaQuestion triviaQuestion) {
        //Create masked question
        Question question = new Question();
        question.setQuestionId();
        question.setQuestion(escape(triviaQuestion.getQuestion()));
        List<String> possibleAnswers = new ArrayList<>(escapeList(triviaQuestion.getIncorrect_answers()));
        possibleAnswers.add(escape(triviaQuestion.getCorrect_answer()));
        Collections.shuffle(possibleAnswers);
        question.setAnswerChoices(possibleAnswers);
        /*
            Store answers for check,
            alternative to store all questions,
            but then you would store more than needed.
        */
        answers.add(new AnswerRequest(question.getQuestionId(), escape(triviaQuestion.getCorrect_answer())));
        return question;
    }


    private String escape(String text) {
        //Escapes html
        return StringEscapeUtils.unescapeHtml4(text);
    }

    private List<String> escapeList (List<String> textList){
        return textList.stream()
                .map(StringEscapeUtils::unescapeHtml4)
                .collect(Collectors.toList());
    }

    private void handleApiError(int responseCode) {
        switch (responseCode) {
            case 0:
                break;
            case 1:
                throw new RuntimeException("Code 1: No Results - The API doesn't have enough questions for your query.");
            case 2:
                throw new RuntimeException("Code 2: Invalid Parameter - Contains an invalid parameter.");
            case 3:
                throw new RuntimeException("Code 3: Token Not Found - Session Token does not exist.");
            case 4:
                throw new RuntimeException("Code 4: Token Empty - Session Token has returned all possible questions for the specified query. Resetting the Token is necessary.");
            case 5:
                throw new RuntimeException("Code 5: Rate Limit - Too many requests have occurred. Each IP can only access the API once every 5 seconds.");
            default:
                throw new RuntimeException("Unexpected response code: " + responseCode);
        }
    }
}
