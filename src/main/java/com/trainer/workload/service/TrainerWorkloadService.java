package com.trainer.workload.service;

import com.trainer.workload.model.Month;
import com.trainer.workload.model.TrainerWorkload;
import com.trainer.workload.model.TrainerWorkloadRequest;
import com.trainer.workload.model.TrainerWorkloadResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TrainerWorkloadService {

    private final Map<String, TrainerWorkload> trainerWorkloads = new HashMap<>();


    public void processWorkload(TrainerWorkloadRequest request) {
        String trainerUsername = request.getTrainerUsername();
        int trainingYear = request.getTrainingDate().getYear();
        Month trainingMonth = Month.values()[request.getTrainingDate().getMonthValue() - 1];

        trainerWorkloads.putIfAbsent(trainerUsername, new TrainerWorkload(trainerUsername));

        TrainerWorkload trainerWorkload = trainerWorkloads.get(trainerUsername);

        trainerWorkload.updateMonthlyWorkload(trainingYear, trainingMonth.ordinal(), request.getTrainingDuration(), request.getActionType());
    }

    public TrainerWorkloadResponse getTrainerWorkload(String trainerUsername, int year, Month month) {
        TrainerWorkload trainerWorkload = trainerWorkloads.get(trainerUsername);

        if (trainerWorkload != null) {
            int totalHours = trainerWorkload.getMonthlyWorkload(year, month.ordinal());
            return new TrainerWorkloadResponse(trainerUsername, year, month, totalHours);
        }
        return new TrainerWorkloadResponse(trainerUsername, year, month, 0);
    }

    public List<String> getAllTrainers() {
        return new ArrayList<>(trainerWorkloads.keySet());
    }

    public List<TrainerWorkloadResponse> getAllTrainerWorkloads() {
        List<TrainerWorkloadResponse> workloadResponses = new ArrayList<>();

        // Iterate over all trainers in the in-memory database
        for (Map.Entry<String, TrainerWorkload> entry : trainerWorkloads.entrySet()) {
            String trainerUsername = entry.getKey();
            TrainerWorkload trainerWorkload = entry.getValue();

            // Generate responses for each year and month in the workload map
            trainerWorkload.getMonthlyWorkload().forEach((year, monthlyWorkload) -> {
                monthlyWorkload.forEach((month, totalHours) -> {
                    // Create a TrainerWorkloadResponse for each year/month/totalHours
                    workloadResponses.add(new TrainerWorkloadResponse(trainerUsername, year, month, totalHours));
                });
            });
        }
        return workloadResponses;
    }


}