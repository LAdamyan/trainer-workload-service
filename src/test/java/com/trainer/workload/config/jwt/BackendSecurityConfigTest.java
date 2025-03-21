package com.trainer.workload.config.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(BackendSecurityConfig.class)
//class BackendSecurityConfigTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Mock
//    private BackendJwtFilter backendJwtFilter;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void shouldPermitAllForWorkloadEndpoint() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/workload"))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    void shouldAuthenticateOtherEndpoints() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/other"))
//                .andExpect(status().isForbidden());
//    }
//
//}