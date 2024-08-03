package com.p2p_lending.p2p_backend.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Data
public class Transaction {

    @MongoId
    private String transactionId;
    private String userId;
    private float transactionAmount; // The total amount involved in this transaction
    private float commissionCharged = 0.15f; // The commission charged for this transaction
    private Date date = new Date();

}
