package com.erick.minibankingmicroservice.service;

import com.erick.minibankingmicroservice.dto.CustomerRes;
import com.erick.minibankingmicroservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepo;

    @Override
    public CustomerRes getCustomerByNik(String nik) {
        return customerRepo.getCustomerByNIK(nik);
    }
}
