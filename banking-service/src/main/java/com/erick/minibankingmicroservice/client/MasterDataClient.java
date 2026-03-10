package com.erick.minibankingmicroservice.client;

import com.erick.minibankingmicroservice.dto.MasterDataReq;
import com.erick.minibankingmicroservice.dto.MasterDataRes;
import com.erick.minibankingmicroservice.response.ApiResponse;
import com.erick.minibankingmicroservice.transfer.BiFastTransfer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MasterDataClient {

    private static final Logger logger = LoggerFactory.getLogger(BiFastTransfer.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper om;
    private final String MASTER_DATA_BASE_URL = "http://master-data-service:8082/master-data";
    private final String API_KEY = "erick_test_gateway_777";

    public BigDecimal getFee(String key, String group) {
        String url = MASTER_DATA_BASE_URL + "/value";

        MasterDataReq req = new MasterDataReq();
        req.setKey(key);
        req.setGroup(group);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MasterDataReq> entity = new HttpEntity<>(req, headers);

        ResponseEntity<ApiResponse<String>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<>() {
                        }
                );
        logger.info("getFee res: {}", response);

        return new BigDecimal(response.getBody().getData());

    }

    public MasterDataRes getMasterData(String key, String group) {
        String url = MASTER_DATA_BASE_URL + "/data";

        MasterDataReq req = new MasterDataReq();
        req.setKey(key);
        req.setGroup(group);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MasterDataReq> entity = new HttpEntity<>(req, headers);

        ResponseEntity<ApiResponse<MasterDataRes>> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.POST,
                        entity,
                        new ParameterizedTypeReference<>() {
                        }
                );
        return response.getBody().getData();

//        return new MasterDataRes(response.getBody().getData());
    }

}
