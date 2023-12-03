package nl.rmjb.triviabackend.views;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AnswerRequestTest {

    @Test
    void testAnswerRequestConstructor() {
        UUID questionId = UUID.randomUUID();
        String answer = "Test Answer";

        AnswerRequest answerRequest = new AnswerRequest(questionId, answer);

        assertNotNull(answerRequest);
        assertEquals(questionId, answerRequest.getQuestionId());
        assertEquals(answer, answerRequest.getAnswer());
    }
}
