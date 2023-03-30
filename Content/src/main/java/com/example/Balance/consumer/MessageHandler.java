package com.example.Balance.consumer;

import com.example.Balance.dto.BalanceDTO;
import com.example.Balance.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageHandler {

    @Autowired
    private BalanceService balanceService;

    /**
     *
     * @param message
     * @throws Exception
     */
    public void handle(Message message) throws Exception {
        if (message.getCode().equals(MessageCode.SUBSCRIBED) || message.getCode().equals(MessageCode.CHANGED_PLAN)) {
            BalanceDTO balanceDTO = new BalanceDTO();
            balanceDTO.setUserId(message.getUserId());
            balanceDTO.setSubscribed(true);
            switch (message.getPlanType()) {
                case PLAN1:
                    balanceDTO.setQuizzesNumber(15);
                    balanceDTO.setFlashCardDecksNumber(8);
                    balanceDTO.setTextbookSolutionsNumber(4);
                    break;
                case PLAN2:
                    balanceDTO.setQuizzesNumber(25);
                    balanceDTO.setFlashCardDecksNumber(15);
                    balanceDTO.setTextbookSolutionsNumber(6);
                    break;
                case PLAN3:
                    balanceDTO.setQuizzesNumber(35);
                    balanceDTO.setFlashCardDecksNumber(20);
                    balanceDTO.setTextbookSolutionsNumber(10);
                    break;
                default:
                    break;
            }
            balanceService.updateBalance(message.getUserId(), balanceDTO);
        } else if (message.getCode().equals(MessageCode.CANCELED)) {
            balanceService.cancelSubscription(message.getUserId());
        } else
            System.out.println("ERROR MESSAGE");
    }
}
