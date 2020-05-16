package com.calarix.sample.testing.customer.service;

import com.calarix.sample.testing.common.exception.CalarixException;
import com.calarix.sample.testing.customer.Customer;
import com.calarix.sample.testing.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer save(Customer customer) {
        Customer customer1 = customerRepository.findByEmail(customer.email.toUpperCase());

        if(customer1 != null){
            throw new CalarixException("invalidCustomer", CalarixException.CUSTOMER_ALREADY_EXIST);
        }
        return customerRepository.save(customer) ;
    }

    @Override
    public List<Customer> findByFullName(String name) {
        if(name == null && name.isEmpty()){
            return customerRepository.findAll();
        }else{
            return customerRepository.findByFullName(name.toUpperCase());
        }
    }
}
