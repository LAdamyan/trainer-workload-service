package com.trainer.workload.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TrainerWorkloadRequestTest {

    @Test
    void testTrainerWorkloadRequestInitialization() {
        TrainerWorkloadRequest request = new TrainerWorkloadRequest(
                "trainer1", "John", "Doe", true,
                LocalDate.of(2023, 1, 15), 60, ActionType.ADD
        );

        assertNotNull(request);
        assertEquals("trainer1", request.getUsername());
        assertEquals("John", request.getFirstName());
        assertEquals("Doe", request.getLastName());
        assertEquals(true, request.isActive());
        assertEquals(LocalDate.of(2023, 1, 15), request.getTrainingDate());
        assertEquals(60, request.getTrainingDuration());
        assertEquals(ActionType.ADD, request.getActionType());
    }

}