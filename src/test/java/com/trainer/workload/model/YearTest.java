package com.trainer.workload.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class YearTest {

    private Year year;

    @BeforeEach
    void setUp() {
        Map<Month, TrainingSummary> monthlyTrainingHours = new HashMap<>();
        monthlyTrainingHours.put(Month.JANUARY, new TrainingSummary(10));
        monthlyTrainingHours.put(Month.FEBRUARY, new TrainingSummary(20));

        year = new Year(1L, 2023, monthlyTrainingHours);
    }

    @Test
    void testYearInitialization() {
        assertNotNull(year);
        assertEquals(1L, year.getId());
        assertEquals(2023, year.getTrainingYear());
        assertNotNull(year.getMonthlyTrainingHours());
        assertEquals(2, year.getMonthlyTrainingHours().size());
    }

    @Test
    void testGetMonthlyTrainingHours() {
        TrainingSummary januarySummary = year.getMonthlyTrainingHours().get(Month.JANUARY);
        TrainingSummary februarySummary = year.getMonthlyTrainingHours().get(Month.FEBRUARY);

        assertNotNull(januarySummary);
        assertNotNull(februarySummary);
        assertEquals(10, januarySummary.getDuration());
        assertEquals(20, februarySummary.getDuration());
    }
}