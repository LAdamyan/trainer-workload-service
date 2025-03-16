package com.trainer.workload.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerWorkload {
    private String username;
    private String firstName;
    private String lastName;
    private boolean isActive;
    private Map<Integer, Map<Month, Integer>> workload;
    private int totalDuration;

    public TrainerWorkload(String username, String firstName, String lastName, boolean active) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = active;
        this.workload = new ConcurrentHashMap<>();
    }

    public <K, V> TrainerWorkload(String username, String firstName, String lastName, boolean active, HashMap<K,V> kvHashMap) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = active;
        this.workload = workload != null ? new ConcurrentHashMap<>(workload) : new ConcurrentHashMap<>();
        this.totalDuration = calculateTotalDuration(this.workload);
    }

    public static TrainerWorkload fromEntity(TrainerWorkload trainer) {
        if (trainer == null) {
            throw new IllegalArgumentException("Trainer cannot be null");
        }

        return new TrainerWorkload(
                trainer.getUsername(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.isActive(),
                trainer.getWorkload() != null ? trainer.getWorkload() : new ConcurrentHashMap<>(),
                calculateTotalDuration(trainer.getWorkload() != null ? trainer.getWorkload() : new ConcurrentHashMap<>())
        );
}
    private static int calculateTotalDuration(Map<Integer, Map<Month, Integer>> workload) {
        return workload.values().stream()
                .flatMap(yearlyData -> yearlyData.values().stream())
                .mapToInt(Integer::intValue)
                .sum();
    }


}
