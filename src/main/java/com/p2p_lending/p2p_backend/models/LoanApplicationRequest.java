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
    private List<BusinessData> pastBusinessData; // List of data for past businesses associated with the applicant
    private BusinessData currentBusinessData; // Data for the current business for which the loan is being applied
    private Boolean isNewBusiness; // Indicates if this loan application is for a new business
    private Boolean pastBusinesses; // Indicates if the applicant has any past businesses
    private float loanAmount; // The amount of loan requested by the applicant
    private float projectedRevenue; // The projected annual revenue of the business
    private int age; // age of the applicant
    private int employeeCount; // The number of employees in the business
    private String UserId;
}
