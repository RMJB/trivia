package nl.rmjb.triviabackend.models;

import java.io.Serializable;
import java.util.List;

public class QuestionListAPI implements Serializable {

    private int responseCode;
    private List<QuestionRaw> questionRaws;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public List<QuestionRaw> getResults() {
        return questionRaws;
    }

    public void setResults(List<QuestionRaw> questionRaws) {
        this.questionRaws = questionRaws;
    }
}