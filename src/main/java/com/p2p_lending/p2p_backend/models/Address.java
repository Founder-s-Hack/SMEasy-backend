package com.p2p_lending.p2p_backend.models;

import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class Address {

    private String addressLine1;
    @Nullable
    private String addressLine2;
    private String suburb;
    private String postcode;
    private String country;

}
