package com.cinqict.workshop.controller;

import com.cinqict.workshop.service.EmployeeService;
import com.cinqict.workshop.service.LegoBoxsetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class EmployeeControllerIT {

    @Autowired
    WebApplicationContext wac;
    MockMvc mockMvc;
    @MockBean
    EmployeeService employeeService;
    @MockBean
    LegoBoxsetService legoBoxsetService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    void retrieveAll() throws Exception {
        mockMvc.perform(get("/employee").with(httpBasic("spring", "password")))
                .andExpect(status().isOk());
    }
}
