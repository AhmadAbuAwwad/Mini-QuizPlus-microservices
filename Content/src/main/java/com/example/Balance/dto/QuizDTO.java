package com.example.Balance.dto;

import com.example.Balance.models.Difficulty;
import lombok.Data;

@Data
public class QuizDTO {
    private Long quizId;
    String topic;
    String quizName;
    String answer;
    Difficulty difficulty;
}