package com.erick.masterdataservice.service;

import com.erick.masterdataservice.entity.MasterData;
import com.erick.masterdataservice.repository.MasterDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MasterDataServiceImpl implements MasterDataService {

    private final MasterDataRepository masterDataRepo;

    @Override
    public String getValue(String key, String group) {
        MasterData data = masterDataRepo
                .findByKeyAndGroupName(key, group)
                .orElseThrow(() -> new RuntimeException("Master data not found"));

        return data.getValue();
    }

    @Override
    public MasterData getMasterData(String key, String group) {
        return masterDataRepo
                .findByKeyAndGroupName(key, group)
                .orElseThrow(() -> new RuntimeException("Master data not found"));
    }
}
