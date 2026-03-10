package com.erick.masterdataservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MasterDataReq {

    @NotBlank
    private String key;

    @NotBlank
    private String group;
}
