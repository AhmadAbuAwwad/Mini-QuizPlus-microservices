package com.example.Balance.dto;

import com.example.Balance.models.Language;
import lombok.Data;

@Data
public class TextBookDTO {
    private Long bookId;
    private String isbn;
    private Language language;
    private String edition;
    private String title;
}