package com.p2p_lending.p2p_backend.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
public class Transaction {

    @MongoId
    private String transactionId;
    private String userId;
    private float transactionAmount;
    private float commissionCharged = 0.9f;
    private Date date = new Date();

}
