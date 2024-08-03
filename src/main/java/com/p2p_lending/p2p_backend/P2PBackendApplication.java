package com.p2p_lending.p2p_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableMongoRepositories
public class P2PBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(P2PBackendApplication.class, args);
    }

}
