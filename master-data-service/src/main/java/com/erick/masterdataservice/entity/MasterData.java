package com.erick.masterdataservice.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "master_data")
@Data
@IdClass(MasterDataId.class)
public class MasterData {

    @Id
    private String key;

    @Id
    @Column(name = "group_name")
    private String groupName;

    private String value;

    @Column(name = "order_no")
    private Integer order;
}
