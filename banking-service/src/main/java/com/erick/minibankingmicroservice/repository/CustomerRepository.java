package com.erick.minibankingmicroservice.repository;

import com.erick.minibankingmicroservice.dto.CustomerRes;
import com.erick.minibankingmicroservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query(value = """
            SELECT c.national_id, c.name
            FROM customers c
            where c.national_id = :nik
            """, nativeQuery = true)
    CustomerRes getCustomerByNIK(String nik);
}
