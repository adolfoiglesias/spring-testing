package com.calarix.sample.testing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    public MockHttpServletRequestBuilder doGetJSON(String url) throws Exception {
       return get(url).contentType(MediaType.APPLICATION_JSON);
    }

    public MockHttpServletRequestBuilder doPostJSON(String url, Object object) throws Exception {
        return post(url).content(asJsonString(object))
                        .contentType(MediaType.APPLICATION_JSON);
    }
    public ResultActions performOk(RequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }
    public ResultActions perform(RequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder);
    }

    protected String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }






}
