package com.trainer.workload.config.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BackendJwtFilter.class)
class BackendJwtFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private JwtTokenValidator jwtTokenValidator;

    @BeforeEach
    void setUp() {
        Mockito.when(jwtTokenValidator.validateBackendToken(Mockito.anyString())).thenReturn(true);
    }

    @Test
    void shouldReturnUnauthorizedWhenTokenIsInvalid() throws Exception {
        Mockito.when(jwtTokenValidator.validateBackendToken(Mockito.anyString())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/workload")
                        .header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturnUnauthorizedWhenAuthorizationHeaderIsMissing() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/workload"))
                .andExpect(status().isUnauthorized());

    }  }