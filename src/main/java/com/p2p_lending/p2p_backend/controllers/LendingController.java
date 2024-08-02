package com.p2p_lending.p2p_backend.controllers;

import com.p2p_lending.p2p_backend.models.User;
import com.p2p_lending.p2p_backend.services.LendingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LendingController {

    private final LendingService lendingService;

    public LendingController(LendingService lendingService) {
        this.lendingService = lendingService;
    }

    @GetMapping("/test")
    public String testEndpoint() {
        return "Test Successful";
    }

    @PostMapping("/post-test")
    public String postEndpoint(@RequestBody User user) {
        return this.lendingService.processUser(user);
    }

}
