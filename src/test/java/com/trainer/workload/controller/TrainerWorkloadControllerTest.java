package com.trainer.workload.controller;

import com.trainer.workload.model.TrainerWorkload;
import com.trainer.workload.service.TrainerWorkloadService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(TrainerWorkloadController.class)
class TrainerWorkloadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TrainerWorkloadService trainerWorkloadService;

    private TrainerWorkload mockTrainerWorkload;

    @BeforeEach
    void setUp() {
        MDC.put("transactionId", "test-transaction-id");

    }
}