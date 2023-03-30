package com.example.Balance.models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book")
public class TextBook {

    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "language")
    @Enumerated(EnumType.STRING)
    private Language language;

    @Column(name = "edition")
    private String edition;

    @Column(name = "title")
    private String title;

    @OneToMany(mappedBy = "textBook", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Question> questionsBooks;
}