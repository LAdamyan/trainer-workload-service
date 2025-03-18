package com.trainer.workload.service;

import com.trainer.workload.model.ActionType;
import com.trainer.workload.model.Month;
import com.trainer.workload.model.TrainerWorkloadRequest;
import com.trainer.workload.model.TrainerWorkloadResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainerWorkloadServiceTest {

    private TrainerWorkloadService trainerWorkloadService;

    @BeforeEach
    public void setUp() {
        trainerWorkloadService = new TrainerWorkloadService();
    }

    @Test
    public void testProcessWorkload_addWorkload() {

        String trainerUsername = "trainer1";
        LocalDate trainingDate = LocalDate.of(2023, 9, 22); // September 2023
        int trainingDuration = 2; // 2 hours
        ActionType actionType = ActionType.ADD; // Assuming ADD is defined somewhere

        TrainerWorkloadRequest request = new TrainerWorkloadRequest(trainerUsername,null,null,true,trainingDate,trainingDuration,actionType);

        trainerWorkloadService.processWorkload(request);

        TrainerWorkloadResponse response = trainerWorkloadService.getTrainerWorkload(trainerUsername, 2023, Month.SEPTEMBER);

        assertEquals(trainerUsername, response.getTrainerUsername());
        assertEquals(2023, response.getYear());
        assertEquals(Month.SEPTEMBER, response.getMonth());
        assertEquals(2, response.getTotalHours()); // Workload should be updated to 2 hours
    }

    @Test
    public void testProcessWorkload_removeWorkload() {
        // Prepare the initial workload
        String trainerUsername = "trainer1";
        LocalDate trainingDate = LocalDate.of(2023, 9, 22); // September 2023
        int trainingDuration = 2; // 2 hours
        ActionType actionType = ActionType.ADD; // Assuming ADD is defined somewhere
        TrainerWorkloadRequest requestAdd = new TrainerWorkloadRequest(trainerUsername,null,null,true,trainingDate,trainingDuration,actionType);

        trainerWorkloadService.processWorkload(requestAdd);

        ActionType removeActionType = ActionType.DELETE;
        TrainerWorkloadRequest requestRemove = new TrainerWorkloadRequest(trainerUsername,null,null,true,trainingDate,trainingDuration,removeActionType);
        trainerWorkloadService.processWorkload(requestRemove);

        TrainerWorkloadResponse response = trainerWorkloadService.getTrainerWorkload(trainerUsername, 2023, Month.SEPTEMBER);

        assertEquals(trainerUsername, response.getTrainerUsername());
        assertEquals(2023, response.getYear());
        assertEquals(Month.SEPTEMBER, response.getMonth());
        assertEquals(0, response.getTotalHours());
    }

    @Test
    public void testGetTrainerWorkload_noWorkload() {
        String trainerUsername = "trainer2";
        TrainerWorkloadResponse response = trainerWorkloadService.getTrainerWorkload(trainerUsername, 2023, Month.OCTOBER);

        assertEquals(trainerUsername, response.getTrainerUsername());
        assertEquals(2023, response.getYear());
        assertEquals(Month.OCTOBER, response.getMonth());
        assertEquals(0, response.getTotalHours());
    }


}
