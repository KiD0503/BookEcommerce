package com.book.admin.user.service;

import com.book.admin.user.repository.CustomerRepository;
import com.book.common.entity.Customer;
import com.book.common.exception.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CustomerService {
    public static final int CUSTOMERS_PER_PAGE = 10;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public Page<Customer> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, CUSTOMERS_PER_PAGE, sort);

        if (keyword != null) {
            return customerRepository.findAll(keyword, pageable);
        }

        return customerRepository.findAll(pageable);
    }

    public void updateCustomerEnabledStatus(Integer id, boolean enabled) {
        customerRepository.updateEnabledStatus(id, enabled);
    }

    public Customer get(Integer id) throws CustomerNotFoundException {
        try {
            return customerRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new CustomerNotFoundException("Could not find any customers with ID " + id);
        }
    }

    public boolean isEmailUnique(Integer id, String email) {
        Customer existCustomer = customerRepository.findByEmail(email);

        if (existCustomer != null && existCustomer.getId() != id) {
            // found another customer having the same email
            return false;
        }

        return true;
    }

    public void save(Customer customerInForm) {
        Customer customerInDB = customerRepository.findById(customerInForm.getId()).get();
        if (!customerInForm.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(customerInForm.getPassword());
            customerInForm.setPassword(encodedPassword);
        } else {
            customerInForm.setPassword(customerInDB.getPassword());
        }
        customerInForm.setEnabled(customerInDB.isEnabled());
        customerInForm.setCreatedTime(customerInDB.getCreatedTime());
        customerInForm.setVerificationCode(customerInDB.getVerificationCode());
        customerInForm.setAuthenticationType(customerInDB.getAuthenticationType());
        customerRepository.save(customerInForm);
    }

    public void delete(Integer id) throws CustomerNotFoundException {
        Long count = customerRepository.countById(id);
        if (count == null || count == 0) {
            throw new CustomerNotFoundException("Could not find any customers with ID " + id);
        }

        customerRepository.deleteById(id);
    }

}
