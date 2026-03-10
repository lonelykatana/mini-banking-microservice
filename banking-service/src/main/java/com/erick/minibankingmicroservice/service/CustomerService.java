package com.erick.minibankingmicroservice.service;

import com.erick.minibankingmicroservice.dto.CustomerRes;

public interface CustomerService {

    CustomerRes getCustomerByNik(String nik);
}
