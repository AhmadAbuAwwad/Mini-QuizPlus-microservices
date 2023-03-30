package com.example.Balance.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @Column(name = "quiz_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizId;

    @Column(name = "topic")
    String topic;

    @Column(name = "quiz_name")
    String quizName;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    Set<Question> questionsSet;
}