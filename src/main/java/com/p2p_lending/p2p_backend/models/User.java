package com.p2p_lending.p2p_backend.models;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class User {
    private String name;
    private String phno;
}
