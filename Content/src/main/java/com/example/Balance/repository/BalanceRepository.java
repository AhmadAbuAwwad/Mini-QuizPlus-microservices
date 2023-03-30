package com.example.Balance.repository;

import com.example.Balance.dto.BalanceDTO;
import com.example.Balance.models.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    /**
     *
     * @return
     */
    @Query("select b from Balance b")
    List<Balance> getAllBalances();

    /**
     *
     * @param id
     * @return
     */
    @Query("select b from Balance b where b.userId = :id")
    Balance getBalanceByUserId(Long id);
}
