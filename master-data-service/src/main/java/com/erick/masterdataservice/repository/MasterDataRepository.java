package com.erick.masterdataservice.repository;

import com.erick.masterdataservice.entity.MasterData;
import com.erick.masterdataservice.entity.MasterDataId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MasterDataRepository extends JpaRepository<MasterData, MasterDataId> {
    Optional<MasterData> findByKeyAndGroupName(String key, String groupName);
}
