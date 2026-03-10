package com.erick.masterdataservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterDataId implements Serializable {

    private String key;
    private String groupName;
}
