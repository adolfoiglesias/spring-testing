package com.calarix.sample.testing.customer.controller;


import com.calarix.sample.testing.common.exception.CalarixException;
import com.calarix.sample.testing.customer.Customer;
import com.calarix.sample.testing.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/customer/")
public class CustomerController  extends com.calarix.pipa.common.web.BaseController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("search/name")
    public ResponseEntity findByFullName(@RequestParam("fullName") String fullName){

        List<Customer> customers = customerService.findByFullName(fullName);
        return buildResponseEntityOK(customers);
    }

    @PostMapping("save")
    public ResponseEntity save(@RequestBody Customer customer){
       try{
           customer = customerService.save(customer);
           return buildResponseEntityOK(customer);
       }catch(CalarixException e){
          return buildResponseEntityError(e.code, e.getMessage());
       }
    }
}
