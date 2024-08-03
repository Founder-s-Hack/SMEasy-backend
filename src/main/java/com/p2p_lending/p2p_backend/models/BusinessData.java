package com.p2p_lending.p2p_backend.models;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.Map;

@Data
public class BusinessData {

    @Nullable
    private Map<String, Double> monthlyRevenue; // Monthly revenue data with month as the key and amount as the value (nullable for new businesses)
    private String businessName;
    @Nullable
    private Duration yearsInOperation; // Duration of business operation (nullable for new businesses)
    private String abn;
    private Address address;
    private String anzsicCode;

}
