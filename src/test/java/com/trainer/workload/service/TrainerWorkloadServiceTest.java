package com.trainer.workload.service;

import com.trainer.workload.model.ActionType;
import com.trainer.workload.model.Month;
import com.trainer.workload.model.TrainerWorkload;
import com.trainer.workload.model.TrainerWorkloadRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TrainerWorkloadServiceTest {

    private TrainerWorkloadService trainerWorkloadService;

    @BeforeEach
    void setUp() {
        trainerWorkloadService = new TrainerWorkloadService();
    }

    @Test
    void testProcessWorkloadAdd() {
        TrainerWorkloadRequest request = new TrainerWorkloadRequest(
                "trainer1", "John", "Doe", true,
                LocalDate.of(2023, 1, 15), 60, ActionType.ADD
        );

        trainerWorkloadService.processWorkload(request);

        int hours = trainerWorkloadService.getMonthlyTrainingHours("trainer1", 2023, Month.JANUARY);
        assertEquals(60, hours);
    }

    @Test
    void testProcessWorkloadDelete() {
        TrainerWorkloadRequest addRequest = new TrainerWorkloadRequest(
                "trainer1", "John", "Doe", true,
                LocalDate.of(2023, 1, 15), 60, ActionType.ADD
        );
        trainerWorkloadService.processWorkload(addRequest);

        TrainerWorkloadRequest deleteRequest = new TrainerWorkloadRequest(
                "trainer1", "John", "Doe", true,
                LocalDate.of(2023, 1, 15), 30, ActionType.DELETE
        );
        trainerWorkloadService.processWorkload(deleteRequest);

        int hours = trainerWorkloadService.getMonthlyTrainingHours("trainer1", 2023, Month.JANUARY);
        assertEquals(30, hours);
    }

    @Test
    void testGetMonthlyTrainingHoursNoData() {
        int hours = trainerWorkloadService.getMonthlyTrainingHours("trainer1", 2023, Month.JANUARY);
        assertEquals(0, hours);
    }

    @Test
    void testGetAllWorkloadData() {
        List<TrainerWorkload> workloadData = trainerWorkloadService.getAllWorkloadData();
        assertTrue(workloadData.isEmpty());
    }

    @Test
    void testGetTrainerWorkload() {
        TrainerWorkloadRequest request = new TrainerWorkloadRequest(
                "trainer1", "John", "Doe", true,
                LocalDate.of(2023, 1, 15), 60, ActionType.ADD
        );
        trainerWorkloadService.processWorkload(request);

        TrainerWorkload trainerWorkload = trainerWorkloadService.getTrainerWorkload("trainer1");
        assertNotNull(trainerWorkload);
        assertEquals("trainer1", trainerWorkload.getUsername());
    }

    @Test
    void testGetTrainerWorkloadNotFound() {
        assertThrows(NoSuchElementException.class, () -> {
            trainerWorkloadService.getTrainerWorkload("nonexistent");
        });
    }

    @Test
    void testDeleteTrainer() {
        TrainerWorkloadRequest request = new TrainerWorkloadRequest(
                "trainer1", "John", "Doe", true,
                LocalDate.of(2023, 1, 15), 60, ActionType.ADD
        );
        trainerWorkloadService.processWorkload(request);

        boolean deleted = trainerWorkloadService.deleteTrainer("trainer1");
        assertTrue(deleted);
        assertThrows(NoSuchElementException.class, () -> {
            trainerWorkloadService.getTrainerWorkload("trainer1");
        });
    }
}