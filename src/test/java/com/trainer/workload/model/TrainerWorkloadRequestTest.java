package com.trainer.workload.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TrainerWorkloadRequestTest {

    @Test
    void testTrainerWorkloadRequest() {
        LocalDate trainingDate = LocalDate.of(2023, 1, 1);
        TrainerWorkloadRequest request = new TrainerWorkloadRequest(
                "trainer1", "John", "Doe", true, trainingDate, 5, ActionType.ADD);

        assertEquals("trainer1", request.getTrainerUsername());
        assertEquals("John", request.getTrainerFirstName());
        assertEquals("Doe", request.getTrainerLastName());
        assertEquals(true, request.isActive());
        assertEquals(trainingDate, request.getTrainingDate());
        assertEquals(5, request.getTrainingDuration());
        assertEquals(ActionType.ADD, request.getActionType());
    }
}