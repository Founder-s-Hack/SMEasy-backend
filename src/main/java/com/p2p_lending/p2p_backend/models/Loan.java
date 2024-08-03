package com.p2p_lending.p2p_backend.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;


@Component
@Data
@Document("Loan")
public class Loan {

    @MongoId
    private String id;

    private String userId;
    private float amount;
    private float balance;
    private boolean paidOff;

}
