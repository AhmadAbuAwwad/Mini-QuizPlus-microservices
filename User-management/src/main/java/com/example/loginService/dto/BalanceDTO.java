package com.example.loginService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BalanceDTO {

    int quizzesNumber;
    boolean subscribed;
    int flashCardDecksNumber;
    int textbookSolutionsNumber;

}
