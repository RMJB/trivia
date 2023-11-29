import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.css']
})
export class QuestionListComponent implements OnInit {
  questions: any[] = [];
  selectedAnswers: { [key: string]: string } = {};
  results: { [key: string]: boolean } = {};
  incorrectAttempts: { [key: string]: boolean } = {};

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/api/trivia/questions').subscribe(
      (data) => {
        this.questions = data;
      },
      (error) => {
        console.error('Error fetching questions', error);
      }
    );
  }

  submitAnswer(questionId: string, selectedAnswer: string): void {
    console.log('Submitting answer for question:', questionId, ' - Selected answer:', selectedAnswer);

    if (selectedAnswer && questionId) {
      const apiUrl = `http://localhost:8080/api/trivia/checkanswers`;
      const payload = { questionId, answer: selectedAnswer };

      this.http.post<any>(apiUrl, payload).subscribe(
        (response) => {
          console.log('Answer response:', response);

          const isCorrect = response.correct;

          if (!isCorrect) {
            this.incorrectAttempts[questionId] = true;
          }

          this.results[questionId] = isCorrect;
        },
        (error) => {
          console.error('Error checking answer', error);
        }
      );
    }
  }

  isCorrectChoice(index: number, question: any): boolean {
    return question.answerChoices[index] === question.answer;
  }
}
