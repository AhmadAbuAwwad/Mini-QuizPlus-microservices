package com.example.Balance.controllers.requests;

import com.example.Balance.dto.QuestionDTO;
import com.example.Balance.models.Difficulty;
import lombok.Data;

import java.util.Set;

@Data
public class QuizRequest {
    private Long quizId;
    private String topic;
    private String quizName;
    private String answer;
    private Difficulty difficulty;
    private Set<QuestionDTO> questionList;
}
