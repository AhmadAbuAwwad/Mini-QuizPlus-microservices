package com.example.Balance.service;

import com.example.Balance.controllers.requests.QuestionRequest;
import com.example.Balance.dto.QuestionDTO;
import com.example.Balance.models.Question;
import com.example.Balance.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    /**
     *
     * @return
     */
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    /**
     *
     * @param questionDTO
     * @return
     */
    public Question createQuestion(QuestionRequest questionDTO) {
        Question question = new Question();
        question.setQuestionId(questionDTO.getQuestionId());
        question.setAnswer(questionDTO.getAnswer());
        question.setText(questionDTO.getText());
        question.setDifficulty(questionDTO.getDifficulty());
//        question.setDifficulty(questionDTO.getDifficulty());
        return questionRepository.saveAndFlush(question);

//        Optional<Question> questionOptional = questionRepository.findById(questionDTO.getQuestionId());
//        if (!questionOptional.isPresent()) throw new RuntimeException("Quiz not found");
//        return questionOptional.get();
    }

    /**
     *
     * @param id
     * @return
     */
    public Question getQuestionById(long id) {
        Optional<Question> questionOptional = questionRepository.findById(id);
        if (!questionOptional.isPresent()) throw new RuntimeException("Question not found");
        return questionOptional.get();
    }

    public Question updateQuestion(long id, QuestionDTO questionDTO) {

        Optional<Question> questionOptional = questionRepository.findById(id);
        if (!questionOptional.isPresent()) {
            throw new RuntimeException();
        } else if (questionOptional.get().getQuestionId() != questionDTO.getQuestionId())
            questionRepository.deleteById(id);

        Question question = new Question();
        question.setQuestionId(questionDTO.getQuestionId());
        question.setAnswer(questionDTO.getAnswer());
        question.setDifficulty(questionDTO.getDifficulty());
        question.setText(questionDTO.getText());
        return questionRepository.saveAndFlush(question);

//        questionOptional = questionRepository.findById(id);
//        if (!questionOptional.isPresent()) throw new RuntimeException("Quiz not found");
//        return questionOptional.get();
    }
}
