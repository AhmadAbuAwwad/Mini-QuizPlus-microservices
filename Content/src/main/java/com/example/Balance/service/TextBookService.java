package com.example.Balance.service;

import com.example.Balance.controllers.requests.QuestionRequest;
import com.example.Balance.controllers.requests.TextBookRequest;
import com.example.Balance.dto.QuestionDTO;
import com.example.Balance.models.Question;
import com.example.Balance.models.TextBook;
import com.example.Balance.repository.TextBookRepository;
import com.example.Balance.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class TextBookService {

    @Autowired
    TextBookRepository textBookRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    Mapper mapper;

    public List<TextBook> getAllBooks() {
        return textBookRepository.getAllTextBooks();
    }


    /**
     * @param textBookRequest
     * @return
     */
    @Transactional
    public TextBook createTextBook(TextBookRequest textBookRequest) {
        TextBook textBook = new TextBook();

        Set<Question> questions = new HashSet<>();
        textBook.setBookId(textBookRequest.getBookId());

        textBookRequest.getQuestionList().stream()
                .forEach(questionDTO -> {
                    Question question = mapper.map(Question.class, questionDTO);
                    question.setTextBook(textBook);
                    questions.add(question);
                });
        textBook.setQuestionsBooks(questions);

        textBook.setIsbn(textBookRequest.getIsbn());
        textBook.setTitle(textBookRequest.getTitle());
        textBook.setEdition(textBookRequest.getEdition());
        textBook.setLanguage(textBookRequest.getLanguage());

        for (QuestionDTO questionDTO : textBookRequest.getQuestionList()) {
            questionService.createQuestion(
                    new QuestionRequest(questionDTO.getQuestionId(), questionDTO.getText(),
                            questionDTO.getDifficulty(), textBookRequest.getBookId(), questionDTO.getAnswer()));
        }

        return textBookRepository.saveAndFlush(textBook);
    }

    /**
     * @param id
     * @return
     */
    public TextBook getTextBookById(long id) {
        Optional<TextBook> textBookOptional = textBookRepository.getTextBookById(id);
        if (!textBookOptional.isPresent())
            throw new RuntimeException("TextBook not found");
        return textBookOptional.get();
    }

    public TextBook updateTextBook(long id, TextBookRequest textBookRequest) {

        Optional<TextBook> textBookOptional = textBookRepository.findById(id);
        if (!textBookOptional.isPresent()) {
            throw new RuntimeException();
        } else if (textBookOptional.get().getBookId() != textBookRequest.getBookId())
            textBookRepository.deleteById(id);

        TextBook textBook = new TextBook();
        textBook.setBookId(textBookRequest.getBookId());
        textBook.setQuestionsBooks(mapper.map(QuestionDTO.class, textBookRequest.getQuestionList()));
        textBook.setIsbn(textBookRequest.getIsbn());
        textBook.setTitle(textBookRequest.getTitle());
        textBook.setEdition(textBookRequest.getEdition());

        return textBookRepository.saveAndFlush(textBook);
    }
}
