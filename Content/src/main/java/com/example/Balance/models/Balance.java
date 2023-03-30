package com.example.Balance.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "balance")
public class Balance {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "quizzes_number")
    int quizzesNumber;

    @Column(name = "subscribed")
    boolean subscribed;

    @Column(name = "flash_card_decks_number")
    int flashCardDecksNumber;

    @Column(name = "textbook_solutions ")
    int textbookSolutionsNumber;
}