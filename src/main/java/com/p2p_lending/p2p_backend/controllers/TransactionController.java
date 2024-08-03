package com.p2p_lending.p2p_backend.controllers;

import com.p2p_lending.p2p_backend.models.Transaction;
import com.p2p_lending.p2p_backend.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController (TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/validation")
    public String postEndpoint(@RequestBody Transaction transaction) {
        return this.transactionService.processTransaction(transaction);
    }
}
