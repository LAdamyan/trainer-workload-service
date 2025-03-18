package com.trainer.workload.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
@Slf4j
@Data
@NoArgsConstructor
public class TrainerWorkload {
    private String username;
    private Map<Integer, Map<Month, Integer>> monthlyWorkload = new HashMap<>();

    public TrainerWorkload(String username) {
        this.username = username;
    }

    public void updateMonthlyWorkload(int year, int month, int duration, ActionType actionType) {
        Month monthEnum = Month.values()[month - 1]; // Convert month integer to enum
        monthlyWorkload.putIfAbsent(year, new HashMap<>()); // Ensure year exists
        Map<Month, Integer> yearWorkload = monthlyWorkload.get(year); // Get workload for the year

        if (actionType == ActionType.ADD) {
            yearWorkload.merge(monthEnum, duration, Integer::sum); // Add hours for the month
        } else if (actionType == ActionType.DELETE) {
            yearWorkload.merge(monthEnum, -duration, Integer::sum); // Subtract hours for the month

            // Remove the month if its total hours <= 0
            if (yearWorkload.get(monthEnum) <= 0) {
                yearWorkload.remove(monthEnum);
                log.info("[TrainerWorkload] Removed month '{}' from year '{}'", monthEnum, year);
            }

            // Remove the year entry if it has no remaining months
            if (yearWorkload.isEmpty()) {
                monthlyWorkload.remove(year);
                log.info("[TrainerWorkload] Removed year '{}' for trainer '{}'", year, username);
            }
        }
    }

    public int getMonthlyWorkload(int year, int month) {
        Month monthEnum = Month.values()[month - 1]; // Convert month integer to enum
        return monthlyWorkload.getOrDefault(year, new HashMap<>()).getOrDefault(monthEnum, 0); // Default to zero hours
    }
}