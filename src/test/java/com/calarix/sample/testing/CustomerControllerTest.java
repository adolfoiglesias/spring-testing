package com.calarix.sample.testing;

import com.calarix.sample.testing.customer.Customer;
import com.calarix.sample.testing.customer.controller.CustomerController;
import com.calarix.sample.testing.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;


import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;



import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest extends  ControllerTest{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    public void findByName() throws Exception {

        Customer customer = new Customer();
        customer.fullName = "Adolfo Miguel Iglesias";

        List<Customer> list = Arrays.asList(customer);
/*
        Mockito.when(customerService.findByFullName(customer.fullName))
                .thenReturn(list);
*/
        BDDMockito.given(customerService.findByFullName(customer.fullName))
                .willReturn(list);

        RequestBuilder requestBuilder =
                doGetJSON("/api/customer/search/name")
                        .param("fullName", customer.fullName);

        performOk(requestBuilder)
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].fullName", is(customer.fullName)));
      /*
        mockMvc.perform(get("/api/customer/search/name")
                .param("fullName", customer.fullName)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].fullName", is(customer.fullName)));*/
    }

    @Test
    public void saveNewCustomer() throws Exception {

        Customer customer = new Customer();
        customer.fullName = "Adolfo Miguel Iglesias";
/*
        Mockito.when(customerService.findByFullName(customer.fullName))
                .thenReturn(list);
*/
        given(customerService.save(customer))
                .willReturn(customer);

        mockMvc.perform(post("/api/customer/save")
                .content(asJsonString(customer))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.fullName", is(customer.fullName)));
    }

}
