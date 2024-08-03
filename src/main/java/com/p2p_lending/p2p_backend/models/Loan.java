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
    private float amount; // Total amount of the loan
    private float balance; // Remaining balance to be repaid
    private Boolean paidOff; // Indicates if the loan has been fully repaid

}
