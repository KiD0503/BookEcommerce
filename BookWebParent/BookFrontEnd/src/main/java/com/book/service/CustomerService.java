package com.book.service;

import com.book.common.entity.Customer;
import com.book.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    public boolean isEmailUnique(String email) {
        Customer customer = customerRepo.findByEmail(email);
        return customer == null;
    }
}