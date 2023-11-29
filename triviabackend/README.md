# Trivia Backend

This spring boot backend exposes two endpoints

    GET http://localhost:8080/api/trivia/questions

Returns 5 formatted questions

    POST http://localhost:8080/api/trivia/checkanswers

Accepts a payload with a question id and answer then responds with a correct/inccorrect

The questions are pulled from opentriviadb https://opentdb.com/api.php?amount=5&type=multiple
