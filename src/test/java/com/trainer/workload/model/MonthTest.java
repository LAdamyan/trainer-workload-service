package com.trainer.workload.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonthTest {

    @Test
    void testMonthValues() {
        Month january = Month.valueOf("JANUARY");
        Month december = Month.valueOf("DECEMBER");

        assertNotNull(january);
        assertNotNull(december);
        assertEquals(Month.JANUARY, january);
        assertEquals(Month.DECEMBER, december);
    }

    @Test
    void testMonthCount() {
        Month[] months = Month.values();
        assertEquals(12, months.length);
    }


}