package com.trainer.workload.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerWorkloadResponse {
    private String trainerUsername;
    private int year;
    private Month month;
    private int totalHours;


}
