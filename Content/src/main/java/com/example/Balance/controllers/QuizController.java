package com.example.Balance.controllers;

import com.example.Balance.controllers.requests.QuizRequest;
import com.example.Balance.dto.QuizDTO;
import com.example.Balance.service.QuizService;
import com.example.Balance.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @Autowired
    Mapper mapper;

    /**
     * @param id
     * @return
     */
    @GetMapping("/getQuizById/{id}")
    public ResponseEntity<QuizDTO> getQuiz(@PathVariable long id) {
        return ResponseEntity.ok(mapper.map(QuizDTO.class, quizService.getQuizById(id)));
    }

    /**
     * @param quizRequest
     * @return
     */
    @PostMapping("/createQuiz")
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizRequest quizRequest) {
        return ResponseEntity.ok(mapper.map(QuizDTO.class, quizService.createQuiz(quizRequest)));
    }

    /**
     * @param id
     * @param quizRequest
     * @return
     */
    @PutMapping("/updateQuiz/{id}")
    public ResponseEntity<QuizDTO> updateQuiz(@PathVariable long id, @RequestBody QuizRequest quizRequest) {
        return ResponseEntity.ok(mapper.map(QuizDTO.class, quizService.updateQuiz(id, quizRequest)));
    }


    @GetMapping("/getAllQuizzes")
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        List<QuizDTO> quizDTOS = new ArrayList<>();
        quizService.getAllQuizzes().stream()
                .filter(quiz -> quizDTOS.add(mapper.map(QuizDTO.class, quiz)));
        return ResponseEntity.ok(quizDTOS);
    }
}
