package com.erick.masterdataservice.service;

import com.erick.masterdataservice.entity.MasterData;

public interface MasterDataService {

    String getValue(String key, String group);
    MasterData getMasterData(String key, String group);
}
