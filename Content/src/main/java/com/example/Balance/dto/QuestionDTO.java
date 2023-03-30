package com.example.Balance.dto;

import com.example.Balance.models.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class QuestionDTO {
    private Long questionId;
    private String text;
    private Difficulty difficulty;
    private String answer;
}
