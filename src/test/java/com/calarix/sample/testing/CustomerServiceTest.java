package com.calarix.sample.testing;


import com.calarix.sample.testing.common.exception.CalarixException;
import com.calarix.sample.testing.customer.Customer;
import com.calarix.sample.testing.customer.repository.CustomerRepository;
import com.calarix.sample.testing.customer.service.CustomerService;
import com.calarix.sample.testing.customer.service.CustomerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public CustomerService customerService() {
            return new CustomerServiceImpl();
        }
    }
    @Autowired
    private CustomerService customerService;
    @MockBean
    private CustomerRepository customerRepository;

    @Test
    public void saveCustomerExist(){
        // Test case 0 -> customer in db
        //when & then
        Customer customer = new Customer();
        customer.email = "amich05@gmail.com";
        customer.RFC = "QWERTY123";
        customer.fullName = "Adolfo Miguel Iglesias";

        Mockito.when(customerRepository.findByEmail(customer.email))
                .thenReturn(customer);
        try{
            customer = customerService.save(customer);
            assertNull(customer);
        }catch(CalarixException e){
            assertEquals(e.getMessage(), CalarixException.CUSTOMER_ALREADY_EXIST);;
        }
    }


    @Test
    public void saveCustomerNew(){
        // Test case 0 -> customer in db
        //when & then
        Customer customer = new Customer();
        customer.email = "amich05@gmail.com";
        customer.RFC = "QWERTY123";
        customer.fullName = "Adolfo Miguel Iglesias";

        Mockito.when(customerRepository.save(customer))
                .thenReturn(customer);
        customer = customerService.save(customer);
        assertNotNull(customer);
    }


}
