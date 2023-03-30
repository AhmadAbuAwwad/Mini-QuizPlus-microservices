package com.example.Balance.service;

import com.example.Balance.dto.BalanceDTO;
import com.example.Balance.models.Balance;
import com.example.Balance.repository.BalanceRepository;
import com.example.Balance.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BalanceService {

    @Autowired
    BalanceRepository balanceRepository;

    @Autowired
    Mapper mapper;


    /**
     * @return
     */
    public List<Balance> getAllBalances() {
        return balanceRepository.getAllBalances();
    }


    /**
     * @param balanceDTO
     * @return
     */
    public BalanceDTO createBalance(BalanceDTO balanceDTO) {
        Balance balance = new Balance();
        balance.setUserId(balanceDTO.getUserId());
        balance.setSubscribed(balanceDTO.isSubscribed());
        balance.setQuizzesNumber(balanceDTO.getQuizzesNumber());
        balance.setFlashCardDecksNumber(balanceDTO.getFlashCardDecksNumber());
        balance.setTextbookSolutionsNumber(balanceDTO.getTextbookSolutionsNumber());
        balanceRepository.saveAndFlush(balance);
        return balanceDTO;
    }

    /**
     * @param id
     * @return
     */
    public Balance getBalanceById(long id) {
        Balance balance = balanceRepository.getBalanceByUserId(id);
        if (balance == null) {
            balance = new Balance();
            balance.setUserId(id);

            balanceRepository.saveAndFlush(balance);
        }
        return balance;
    }


    /**
     * @param id
     * @param balanceDTO
     * @return
     */
    public BalanceDTO updateBalance(long id, BalanceDTO balanceDTO) {
        Optional<Balance> balanceOptional = balanceRepository.findById(id);
        Balance balance = null;
        if (!balanceOptional.isPresent()) {
            createBalance(balanceDTO);
        } else {

            balance = new Balance();
            balance.setUserId(balanceOptional.get().getUserId());
            balance.setSubscribed(balanceDTO.isSubscribed());
            balance.setQuizzesNumber(balanceDTO.getQuizzesNumber());
            balance.setFlashCardDecksNumber(balanceDTO.getFlashCardDecksNumber());
            balance.setTextbookSolutionsNumber(balanceDTO.getTextbookSolutionsNumber());
        }
        balanceRepository.saveAndFlush(balance);
        return balanceDTO;
    }

    /**
     * @param id
     * @return
     */
    public Balance cancelSubscription(long id) {

        Optional<Balance> balanceOptional = balanceRepository.findById(id);
        if (!balanceOptional.isPresent()) {
            throw new RuntimeException();
        }

        Balance balance = new Balance();
        balance.setUserId(id);
        balance.setSubscribed(false);
        balance.setQuizzesNumber(0);
        balance.setTextbookSolutionsNumber(0);
        balance.setFlashCardDecksNumber(0);

        return balanceRepository.saveAndFlush(balance);
    }
}
