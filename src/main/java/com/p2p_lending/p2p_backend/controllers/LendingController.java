package com.p2p_lending.p2p_backend.controllers;

import com.p2p_lending.p2p_backend.models.LoanApplicationRequest;
import com.p2p_lending.p2p_backend.services.LendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lending")
@Slf4j
public class LendingController {

    private final LendingService lendingService;

    public LendingController(LendingService lendingService) {
        this.lendingService = lendingService;
    }

    @PostMapping("/validation")
    public List<String> postEndpoint(@RequestBody LoanApplicationRequest request) {
        return this.lendingService.processRequest(request);
    }
}