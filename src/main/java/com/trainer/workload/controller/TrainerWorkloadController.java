package com.trainer.workload.controller;


import com.trainer.workload.model.Month;
import com.trainer.workload.model.TrainerWorkloadRequest;
import com.trainer.workload.model.TrainerWorkloadResponse;
import com.trainer.workload.service.TrainerWorkloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/workload")
public class TrainerWorkloadController {

    private final TrainerWorkloadService trainerWorkloadService;

    public TrainerWorkloadController(TrainerWorkloadService trainerWorkloadService) {
        this.trainerWorkloadService = trainerWorkloadService;
    }

    @PostMapping
    public ResponseEntity<String> updateWorkload(
            @RequestBody TrainerWorkloadRequest request,
            @RequestHeader("Authorization") String token) {

        trainerWorkloadService.processWorkload(request);
        return ResponseEntity.ok("Trainer workload updated successfully.");
    }

    @GetMapping
    public ResponseEntity<TrainerWorkloadResponse> getTrainerWorkload(
            @PathVariable String trainerUsername, // Use @PathVariable
            @RequestParam int year,
            @RequestParam Month month,
            @RequestHeader("Authorization") String token) {

        TrainerWorkloadResponse response = trainerWorkloadService.getTrainerWorkload(trainerUsername, year, month);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-data")
    public ResponseEntity<List<TrainerWorkloadResponse>> getAllTrainerWorkloads(
            @RequestHeader("Authorization") String token) { // Accepts backend token
        List<TrainerWorkloadResponse> responses = trainerWorkloadService.getAllTrainerWorkloads();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/trainers")
    public ResponseEntity<List<String>> getAllTrainers(
            @RequestHeader("Authorization") String token) { // Accepts backend token
        List<String> trainerUsernames = trainerWorkloadService.getAllTrainers();
        return ResponseEntity.ok(trainerUsernames);
    }
}





