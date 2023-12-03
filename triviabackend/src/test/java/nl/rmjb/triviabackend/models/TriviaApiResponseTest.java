package nl.rmjb.triviabackend.models;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class TriviaApiResponseTest {

    @Test
    void testGetterAndSetter() {
        TriviaApiResponse triviaApiResponse = new TriviaApiResponse();

        triviaApiResponse.setResponse_code(200);
        triviaApiResponse.setResults(Arrays.asList(new TriviaQuestion(), new TriviaQuestion()));

        assertEquals(triviaApiResponse.getResponse_code(), 200);
        assertEquals(triviaApiResponse.getResults().size(), 2);
    }
}
