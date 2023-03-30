package com.example.Balance.dto;

import lombok.Data;

@Data
public class BalanceDTO {
    private Long userId;
    int quizzesNumber;
    boolean subscribed;
    int flashCardDecksNumber;
    int textbookSolutionsNumber;
}
