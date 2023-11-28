package nl.rmjb.triviabackend.services;

import nl.rmjb.triviabackend.models.*;
import nl.rmjb.triviabackend.views.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TriviaService {

    public List<FormattedQuestion> getFormattedQuestions(List<Question> questionRaw) {

        return questionRaw.stream()
                .map(question -> new FormattedQuestion(question.getQuestionId(),
                        question.getQuestion(), question.getAnswerChoices()))
                .collect(Collectors.toList());
    }

    public List<Question> getInitialQuestions(QuestionListAPI questions) {
        List<QuestionRaw> questionRaws = questions.getResults();

        List<Question> questionList = questionRaws.stream()
                .map(questionRaw -> new Question(questionRaw.getQuestion(), questionRaw.getCorrect_answer(), questionRaw.getIncorrect_answers()))
                .collect(Collectors.toList());

        questionList.forEach(question -> question
                .setAnswerChoices(question.getIncorrectAnswers(), question.getAnswer()));
        questionList.forEach(Question::setQuestionId);

        return questionList;
    }

    public List<QuestionAnswer> getCorrectAnswers(List<Question> questionList) {
        return questionList.stream()
                .map(question -> new QuestionAnswer(question.getQuestionId(), question.getAnswer()))
                .collect(Collectors.toList());
    }
}