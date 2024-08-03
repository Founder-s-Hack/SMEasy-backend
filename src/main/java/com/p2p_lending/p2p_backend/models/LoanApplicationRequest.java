package com.p2p_lending.p2p_backend.models;

import jakarta.annotation.Nullable;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class LoanApplicationRequest {

    @Nullable
    private List<BusinessData> pastBusinessData;
    private BusinessData currentBusinessData;
    private BusinessStatus businessStatus;
    private Boolean pastBusinesses;
    private float loanAmount;
    private float projectedRevenue;
    private int age;
    private int numberOfJobsCreated;
    private int numberOfJobsRetained;

}
