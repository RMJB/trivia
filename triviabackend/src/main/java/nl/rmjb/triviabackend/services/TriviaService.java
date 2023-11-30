package nl.rmjb.triviabackend.services;

import nl.rmjb.triviabackend.models.*;
import nl.rmjb.triviabackend.views.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TriviaService {

    public List<FormattedQuestion> getFormattedQuestions(List<Question> questionRaw) {
        return questionRaw.stream()
                .map(question -> new FormattedQuestion(
                        question.getQuestionId(),
                        question.getQuestion(),
                        question.getAnswerChoices()))
                .collect(Collectors.toList());
    }

    public List<Question> getInitialQuestions(QuestionListAPI questions) {
        return questions.getResults().stream()
                .map(questionRaw -> {
                    Question question = new Question(
                            questionRaw.getQuestion(),
                            questionRaw.getCorrect_answer(),
                            questionRaw.getIncorrect_answers());
                    //question.setAnswerChoices(question.getIncorrectAnswers(), question.getAnswer());
                    //question.setQuestionId();
                    return question;
                })
                .collect(Collectors.toList());
    }

    public List<QuestionAnswer> getCorrectAnswers(List<Question> questionList) {
        return questionList.stream()
                .map(question -> new QuestionAnswer(question.getQuestionId(), question.getAnswer()))
                .collect(Collectors.toList());
    }
    public String getCorrectAnswer(UUID questionId, List<QuestionAnswer> questionAnswers) {
        return questionAnswers.stream()
                .filter(questionAnswer -> questionId.equals(questionAnswer.getQuestionId()))
                .findFirst()
                .map(QuestionAnswer::getAnswer)
                .orElse(null);
    }
}

