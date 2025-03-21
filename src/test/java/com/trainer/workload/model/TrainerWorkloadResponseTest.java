package com.trainer.workload.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainerWorkloadResponseTest {
    @Test
    void testTrainerWorkloadResponse() {
        TrainerWorkloadResponse response = new TrainerWorkloadResponse(
                "trainer1", 2023, Month.JANUARY, 10);

        assertEquals("trainer1", response.getTrainerUsername());
        assertEquals(2023, response.getYear());
        assertEquals(Month.JANUARY, response.getMonth());
        assertEquals(10, response.getTotalHours());
    }

    @Test
    void testBuilder() {
        TrainerWorkloadResponse response = TrainerWorkloadResponse.builder()
                .trainerUsername("trainer1")
                .year(2023)
                .month(Month.JANUARY)
                .totalHours(10)
                .build();

        assertEquals("trainer1", response.getTrainerUsername());
        assertEquals(2023, response.getYear());
        assertEquals(Month.JANUARY, response.getMonth());

    }
}