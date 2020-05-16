package com.calarix.sample.testing;

import com.calarix.sample.testing.customer.Customer;
import com.calarix.sample.testing.customer.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integration-test.properties")
public class CustomerControllerIntegrationTest extends ControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    // se llama por cada metodo anotado por @Test que exista en la clase
    @Before
    public void setUp() {
        customer = new Customer();
        customer.email = "amich05@gmail.com";
        customer.RFC = "QWERTY123";
        customer.fullName = "Adolfo Miguel Iglesias";

        if(customerRepository.count() == 0){
            customerRepository.save(customer);
        }
    }

    @Test
    public void findByFullName() throws Exception {
        // encontrar Customer -> criteria correcto : Adolfo

        performOk(
                doGetJSON("/api/customer/search/name")
                        .param("fullName", "Adolfo"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].fullName", is(customer.fullName)));

        // encontrar Customer -> criteria correcto : ADOLFO
        performOk(doGetJSON("/api/customer/search/name")
                .param("fullName", "ADOLFO"))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].fullName", is(customer.fullName)));

        // encontrar Customer -> criteria correcto : Chaviano
        performOk(doGetJSON("/api/customer/search/name")
                .param("fullName", "Chaviano"))
                .andExpect(jsonPath("$.data", hasSize(0)));
/*
        mockMvc.perform(get("/api/customer/search/name")
                .param("fullName", "Adolfo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].fullName", is(customer.fullName)));

        // encontrar Customer -> criteria correcto : ADOLFO

        mockMvc.perform(get("/api/customer/search/name")
                .param("fullName", "ADOLFO")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].fullName", is(customer.fullName)));

        // encontrar Customer -> criteria correcto : Chaviano

        mockMvc.perform(get("/api/customer/search/name")
                .param("fullName", "Chaviano")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(0)));
                */

    }


    @Test
    public void saveExistCustomer() throws Exception {

       // no deja insertar customer con email registrado
        mockMvc.perform(post("/api/customer/save")
               .content(asJsonString(customer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", org.hamcrest.Matchers.is("invalidCustomer")));
    }

    @Test
    public void saveNewCustomer() throws Exception {

        //  insertar nuev customer
        Customer newCustomer = new Customer();
        newCustomer = new Customer();
        newCustomer.email = "anaibe@gmail.com";
        newCustomer.RFC = "QWERTY123";
        newCustomer.fullName = "Analia Iglesias Benitez";

        mockMvc.perform(post("/api/customer/save")
                .content(asJsonString(newCustomer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fullName", Matchers.is(newCustomer.fullName)));

    }






}
