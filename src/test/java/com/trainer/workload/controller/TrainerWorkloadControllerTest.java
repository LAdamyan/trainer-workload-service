package com.trainer.workload.controller;


import com.trainer.workload.model.ActionType;
import com.trainer.workload.model.Month;
import com.trainer.workload.model.TrainerWorkloadRequest;
import com.trainer.workload.model.TrainerWorkloadResponse;
import com.trainer.workload.service.TrainerWorkloadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TrainerWorkloadControllerTest {


    private MockMvc mockMvc;

    @Mock
    private TrainerWorkloadService trainerWorkloadService;

    @InjectMocks
    private TrainerWorkloadController trainerWorkloadController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainerWorkloadController).build();
    }

    @Test
    void testUpdateWorkload() throws Exception {
        TrainerWorkloadRequest request = new TrainerWorkloadRequest(
                "trainer1", "John", "Doe", true, LocalDate.of(2023, 1, 1), 5, ActionType.ADD);

        mockMvc.perform(post("/api/v1/workload")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"trainerUsername\":\"trainer1\",\"trainerFirstName\":\"John\",\"trainerLastName\":\"Doe\",\"active\":true,\"trainingDate\":\"2023-01-01\",\"trainingDuration\":5,\"actionType\":\"ADD\"}")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(content().string("Trainer workload updated successfully."));

        verify(trainerWorkloadService, times(1)).processWorkload(any(TrainerWorkloadRequest.class));
    }

    @Test
    void testGetTrainerWorkload() throws Exception {
        TrainerWorkloadResponse response = new TrainerWorkloadResponse("trainer1", 2023, Month.JANUARY, 10);
        when(trainerWorkloadService.getTrainerWorkload(anyString(), anyInt(), any(Month.class))).thenReturn(response);

        mockMvc.perform(get("/api/v1/workload")
                        .param("trainerUsername", "trainer1")
                        .param("year", "2023")
                        .param("month", "JANUARY")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trainerUsername").value("trainer1"))
                .andExpect(jsonPath("$.year").value(2023))
                .andExpect(jsonPath("$.month").value("JANUARY"))
                .andExpect(jsonPath("$.totalHours").value(10));

        verify(trainerWorkloadService, times(1)).getTrainerWorkload("trainer1", 2023, Month.JANUARY);
    }

    @Test
    void testGetAllTrainerWorkloads() throws Exception {
        TrainerWorkloadResponse response = new TrainerWorkloadResponse("trainer1", 2023, Month.JANUARY, 10);
        when(trainerWorkloadService.getAllTrainerWorkloads()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/workload/all-data")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].trainerUsername").value("trainer1"))
                .andExpect(jsonPath("$[0].year").value(2023))
                .andExpect(jsonPath("$[0].month").value("JANUARY"))
                .andExpect(jsonPath("$[0].totalHours").value(10));

        verify(trainerWorkloadService, times(1)).getAllTrainerWorkloads();
    }

    @Test
    void testGetAllTrainers() throws Exception {
        when(trainerWorkloadService.getAllTrainers()).thenReturn(List.of("trainer1"));

        mockMvc.perform(get("/api/v1/workload/trainers")
                        .header("Authorization", "Bearer token"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("trainer1"));

        verify(trainerWorkloadService, times(1)).getAllTrainers();
    }
}