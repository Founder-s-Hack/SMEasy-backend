package com.p2p_lending.p2p_backend.models;

import jakarta.annotation.Nullable;
import lombok.Data;

import java.util.Map;

@Data
public class BusinessData {

    @Nullable
    private Map<String, Double> monthlyRevenue;
    private String businessName;
    @Nullable
    private Duration yearsInOperation;
    private String abn;
    private Address address;
    private String anzsicCode;

}
