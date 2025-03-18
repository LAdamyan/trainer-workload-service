package com.trainer.workload.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class TrainerWorkloadRequest {
    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private boolean isActive;
    private LocalDate trainingDate;
    private int trainingDuration;
    private ActionType actionType;

    public TrainerWorkloadRequest(String trainer1, LocalDate of, int i, ActionType actionType) {
    }
}
