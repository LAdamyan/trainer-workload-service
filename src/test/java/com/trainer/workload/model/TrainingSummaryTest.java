package com.trainer.workload.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainingSummaryTest {
    @Test
    void testTrainingSummaryInitialization() {
        TrainingSummary trainingSummary = new TrainingSummary(60);

        assertNotNull(trainingSummary);
        assertEquals(60, trainingSummary.getDuration());
    }

    @Test
    void testSetDuration() {
        TrainingSummary trainingSummary = new TrainingSummary();
        trainingSummary.setDuration(45);

        assertEquals(45, trainingSummary.getDuration());
    }

}