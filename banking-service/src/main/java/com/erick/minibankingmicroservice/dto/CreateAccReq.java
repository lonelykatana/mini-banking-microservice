package com.erick.minibankingmicroservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateAccReq {

    @NotNull
    private UUID customerId;

    @NotBlank
    private String branchId;

    @NotBlank
    private String branchName;
}
