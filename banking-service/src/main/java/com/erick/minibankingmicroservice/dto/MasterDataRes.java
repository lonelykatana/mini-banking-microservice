package com.erick.minibankingmicroservice.dto;

import lombok.Data;

@Data
public class MasterDataRes {

    private String key;
    private String groupName;
    private String value;
    private Integer order;
}
