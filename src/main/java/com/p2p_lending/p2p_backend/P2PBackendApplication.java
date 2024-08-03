package com.p2p_lending.p2p_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients 
public class P2PBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(P2PBackendApplication.class, args);
    }

}
