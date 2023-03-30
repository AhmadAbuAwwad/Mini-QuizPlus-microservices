package com.example.Balance.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @Column(name = "question_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(name = "text")
    private String text;

    @Column(name = "difficulty")
    private Difficulty difficulty;

    @Column(name = "answer")
    String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    TextBook textBook;

    @ManyToOne(fetch = FetchType.LAZY)
    Quiz quiz;
}
