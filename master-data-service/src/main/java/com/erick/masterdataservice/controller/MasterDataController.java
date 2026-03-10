package com.erick.masterdataservice.controller;

import com.erick.masterdataservice.ApiResponse;
import com.erick.masterdataservice.dto.MasterDataReq;
import com.erick.masterdataservice.entity.MasterData;
import com.erick.masterdataservice.service.MasterDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/master-data")
@RequiredArgsConstructor
public class MasterDataController {

    private final MasterDataService service;

    @PostMapping("/value")
    public ApiResponse<String> getValue(@Valid @RequestBody MasterDataReq req) {
        String result = service.getValue(req.getKey(), req.getGroup());
        return new ApiResponse<>(true, "success", result, LocalDateTime.now());
    }

    @PostMapping("/data")
    public ApiResponse<MasterData> getMasterData(@Valid @RequestBody MasterDataReq req) {
        MasterData data =  service.getMasterData(req.getKey(), req.getGroup());
        return new ApiResponse<>(true, "success", data, LocalDateTime.now());
    }
}
