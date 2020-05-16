package com.calarix.sample.testing;

import com.calarix.sample.testing.customer.Customer;
import com.calarix.sample.testing.customer.repository.CustomerRepository;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRespositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @Before
    public void setUp() {
        customer = new Customer();
        customer.email = "amich05@gmail.com";
        customer.RFC = "QWERTY123";
        customer.fullName = "Adolfo Miguel Iglesias";

        testEntityManager.persist(customer);
        testEntityManager.flush();

    }

    @Test
    public void existCustomer(){
        // Test case 0 -> customer in db
        //when & then
        assertTrue(customerRepository.count() == 1);
    }

    @Test
    public void customerFindByName(){

        //Test Case 1 -> Criteria correct
        //when
        List<Customer> customers = customerRepository.findByFullName("Adolfo Miguel Iglesias".toUpperCase());
        //then
        assertNotNull(customers);
        assertTrue(customers.size() == 1);
        assertTrue(customers.get(0).email.equals(customer.email));

        //Test Case 2 -> Criteria correct
        //when
        customers = customerRepository.findByFullName("ADO".toUpperCase());
        //then
        assertNotNull(customers);
        assertTrue(customers.size() == 1);
        assertTrue(customer.email.equals(customers.get(0).email));

        //Test Case 3 -> Criteria incorrect
        //when
        customers = customerRepository.findByFullName("AMi");
        //then
        assertTrue(customers.size() == 0);
    }

}
