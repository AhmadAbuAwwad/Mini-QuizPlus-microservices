package com.example.Balance.controllers;

import com.example.Balance.dto.BalanceDTO;
import com.example.Balance.service.BalanceService;
import com.example.Balance.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    @Autowired
    BalanceService balanceService;

    @Autowired
    Mapper mapper;

    /**
     * @param id
     * @return
     */
    @GetMapping("/getBalanceByUserId/{id}")
    public ResponseEntity<BalanceDTO> getBalance(@PathVariable long id) {
        return ResponseEntity.ok(mapper.map(BalanceDTO.class, balanceService.getBalanceById(id)));
    }

    /**
     * @param balanceDTO
     * @return
     */
    @PostMapping("/createBalance")
    public ResponseEntity<BalanceDTO> createBalance(@RequestBody BalanceDTO balanceDTO) {
        return ResponseEntity.ok(mapper.map(BalanceDTO.class, balanceService.createBalance(balanceDTO)));
    }

    @PutMapping("/updateBalance/{id}")
    public ResponseEntity<BalanceDTO> updateBalance(@PathVariable long id, @RequestBody BalanceDTO balanceDTO) {
        return ResponseEntity.ok(mapper.map(BalanceDTO.class, balanceService.updateBalance(id, balanceDTO)));
    }


    /**
     * @return
     */
    @GetMapping("/getAllBalances")
    public ResponseEntity<BalanceDTO> getAllBalances() {
        return ResponseEntity.ok(new BalanceDTO());
    }
}
