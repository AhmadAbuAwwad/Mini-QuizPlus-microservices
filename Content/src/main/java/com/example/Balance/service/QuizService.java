package com.example.Balance.service;

import com.example.Balance.controllers.requests.QuestionRequest;
import com.example.Balance.controllers.requests.QuizRequest;
import com.example.Balance.dto.QuestionDTO;
import com.example.Balance.models.Question;
import com.example.Balance.models.Quiz;
import com.example.Balance.repository.QuizRepository;
import com.example.Balance.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionService questionService;


    @Autowired
    Mapper mapper;


    /**
     * @return
     */
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    /**
     * @param quizRequest
     * @return
     */
    public Quiz createQuiz(QuizRequest quizRequest) {
        Quiz quiz = new Quiz();
        quiz.setQuizName(quizRequest.getQuizName());
        quiz.setTopic(quizRequest.getTopic());
        Set<Question> questions = new HashSet<>();
        quizRequest.getQuestionList().stream()
                .forEach(questionDTO -> {
                    Question question = mapper.map(Question.class, questionDTO);
                    question.setQuiz(quiz);
                    questions.add(question);
                });
        quiz.setQuestionsSet(questions);
        return quizRepository.saveAndFlush(quiz);
    }


    /**
     * @param id
     * @return
     */
    public Quiz getQuizById(long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (!quiz.isPresent())
            throw new RuntimeException("Quiz not found");
        return quiz.get();
    }

    public Quiz updateQuiz(long id, QuizRequest quizRequest) {

        Optional<Quiz> quizOptional = quizRepository.findById(id);
        if (!quizOptional.isPresent()) {
            throw new RuntimeException();
        } else if (quizOptional.get().getQuizId() != quizRequest.getQuizId())
            quizRepository.deleteById(id);

        Quiz quiz = new Quiz();
        quiz.setQuizId(quizRequest.getQuizId());
        quiz.setQuizName(quizRequest.getQuizName());
        quiz.setTopic(quizRequest.getTopic());
        quiz.setQuestionsSet(mapper.map(QuizRequest.class, quizRequest.getQuestionList()));

        return quizRepository.saveAndFlush(quiz);
    }
}
