package nl.rmjb.triviabackend.models;

import lombok.Data;

import java.util.List;

@Data
public class TriviaApiResponse {
    private int response_code;
    private List<TriviaQuestion> results;
}