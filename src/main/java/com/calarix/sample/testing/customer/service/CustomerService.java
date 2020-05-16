package com.calarix.sample.testing.customer.service;

import com.calarix.sample.testing.customer.Customer;

import java.util.List;

public interface CustomerService {

    public Customer save(Customer customer);
    public List<Customer> findByFullName(String name);
}
