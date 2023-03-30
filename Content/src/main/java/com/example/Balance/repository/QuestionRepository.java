package com.example.Balance.repository;

import com.example.Balance.dto.BalanceDTO;
import com.example.Balance.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    /**
     *
     * @return
     */
    @Query("select b from Question b")
    List<Question> getAllQuestions();

    /**
     *
     * @param id
     * @return
     */
    @Query("select b from Question b where b.questionId = :id")
    Question getQuestionById(Long id);

}
