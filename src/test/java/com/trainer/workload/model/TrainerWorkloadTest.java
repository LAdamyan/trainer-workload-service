package com.trainer.workload.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class TrainerWorkloadTest {
    private TrainerWorkload trainerWorkload;

    @BeforeEach
    void setUp() {
        trainerWorkload = new TrainerWorkload("trainer1");
    }

    @Test
    void testUpdateMonthlyWorkload_Add() {
        trainerWorkload.updateMonthlyWorkload(2023, 1, 5, ActionType.ADD);
        assertEquals(5, trainerWorkload.getMonthlyWorkload(2023, 1));
    }

    @Test
    void testUpdateMonthlyWorkload_Delete() {
        trainerWorkload.updateMonthlyWorkload(2023, 1, 5, ActionType.ADD);
        trainerWorkload.updateMonthlyWorkload(2023, 1, 3, ActionType.DELETE);
        assertEquals(2, trainerWorkload.getMonthlyWorkload(2023, 1));
    }

    @Test
    void testUpdateMonthlyWorkload_DeleteToZero() {
        trainerWorkload.updateMonthlyWorkload(2023, 1, 5, ActionType.ADD);
        trainerWorkload.updateMonthlyWorkload(2023, 1, 5, ActionType.DELETE);
        assertEquals(0, trainerWorkload.getMonthlyWorkload(2023, 1));
    }

    @Test
    void testUpdateMonthlyWorkload_RemoveYear() {
        trainerWorkload.updateMonthlyWorkload(2023, 1, 5, ActionType.ADD);
        trainerWorkload.updateMonthlyWorkload(2023, 1, 5, ActionType.DELETE);
        Map<Integer, Map<Month, Integer>> workload = trainerWorkload.getMonthlyWorkload();
        assertTrue(workload.isEmpty());
    }
}