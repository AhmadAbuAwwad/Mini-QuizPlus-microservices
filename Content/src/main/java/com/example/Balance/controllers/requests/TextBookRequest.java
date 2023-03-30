package com.example.Balance.controllers.requests;

import com.example.Balance.dto.QuestionDTO;
import com.example.Balance.models.Language;
import com.example.Balance.models.Question;
import lombok.Data;

import java.util.Set;


@Data
public class TextBookRequest {
    private Long bookId;
    private String isbn;
    private Language language;
    private String edition;
    private String title;
    private Set<QuestionDTO> questionList;
}
