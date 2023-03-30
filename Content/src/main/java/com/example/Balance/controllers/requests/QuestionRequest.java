package com.example.Balance.controllers.requests;

import com.example.Balance.models.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class QuestionRequest {
    private Long questionId;
    private String text;
    private Difficulty difficulty;
    private String answer;
    private long quizQuizId;
    private long textBookBookId;

    public QuestionRequest(Long questionId, String text, Difficulty difficulty, String answer, long quizQuizId) {
        this.questionId = questionId;
        this.text = text;
        this.difficulty = difficulty;
        this.answer = answer;
        this.quizQuizId = quizQuizId;
    }

    public QuestionRequest(Long questionId, String text, Difficulty difficulty, long textBookBookId, String answer) {
        this.questionId = questionId;
        this.text = text;
        this.difficulty = difficulty;
        this.answer = answer;
        this.textBookBookId = textBookBookId;
    }
}
