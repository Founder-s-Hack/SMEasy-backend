package com.p2p_lending.p2p_backend.models;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@Document("LoanApplicationRequests")
public class LoanApplicationRequest {

    @MongoId
    private String id;

    @Nullable
    private List<BusinessData> pastBusinessData;
    private BusinessData currentBusinessData;
    private BusinessStatus businessStatus;
    private Boolean pastBusinesses;
    private float loanAmount;
    private float projectedRevenue;
    private int age;
    private int employeeCount;

}
