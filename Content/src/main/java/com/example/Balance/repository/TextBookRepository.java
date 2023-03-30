package com.example.Balance.repository;

import com.example.Balance.models.Question;
import com.example.Balance.models.TextBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TextBookRepository extends JpaRepository<TextBook, Long> {

    /**
     *
      * @return
     */
    @Query("select b from TextBook b")
    List<TextBook>  getAllTextBooks();

    /**
     *
     * @param id
     * @return
     */
    @Query("select b from TextBook b where b.bookId = :id")
    Optional<TextBook> getTextBookById(Long id);
}
