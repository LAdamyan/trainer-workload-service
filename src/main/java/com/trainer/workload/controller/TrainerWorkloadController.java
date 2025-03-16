package com.trainer.workload.controller;

import com.trainer.workload.config.jwt.JwtUtil;
import com.trainer.workload.model.Month;
import com.trainer.workload.model.TrainerWorkload;
import com.trainer.workload.model.TrainerWorkloadRequest;
import com.trainer.workload.service.TrainerWorkloadService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Slf4j
@RestController
@RequestMapping("/api/v1/workload")
public class TrainerWorkloadController {

    private final TrainerWorkloadService trainerWorkloadService;
    private final JwtUtil jwtUtil;

    public TrainerWorkloadController(TrainerWorkloadService trainerWorkloadService, JwtUtil jwtUtil) {
        this.trainerWorkloadService = trainerWorkloadService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> updateWorkload(@RequestBody TrainerWorkloadRequest request,
                                                              @RequestHeader("Authorization") String token) {
        String transactionId = MDC.get("transactionId");
        log.info("[{}] Received updateWorkload request: {}", transactionId, request);

        token = token.replace("Bearer ", "");

        if (!jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid JWT Token"));
        }

        trainerWorkloadService.processWorkload(request);

        return ResponseEntity.ok(Map.of(
                "message", "Trainer workload updated successfully.",
                "transactionId", transactionId
        ));
    }

    @GetMapping("/data")
    public ResponseEntity<List<TrainerWorkload>> getAllWorkloadData() {
        String transactionId = MDC.get("transactionId");
        log.info("[{}] Received getAllWorkloadData request", transactionId);

        List<TrainerWorkload> workloadData = trainerWorkloadService.getAllWorkloadData();
        log.info("[{}] Retrieved workload data successfully, size: {}", transactionId, workloadData.size());

        return ResponseEntity.ok(workloadData);
    }

    @GetMapping("/data/{username}")
    public ResponseEntity<TrainerWorkload> getTrainerWorkload(@PathVariable String username) {
        String transactionId = MDC.get("transactionId");
        log.info("[{}] Fetching workload data for trainer: {}", transactionId, username);

        TrainerWorkload trainerData = trainerWorkloadService.getTrainerWorkload(username);

        return ResponseEntity.ok(trainerData);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Map<String, String>> deleteTrainer(@PathVariable String username, @RequestHeader("Authorization") String token) {
        String transactionId = MDC.get("transactionId");
        log.info("[{}] Deleting workload data for trainer: {}", transactionId, username);

        boolean deleted = trainerWorkloadService.deleteTrainer(username);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "message", "Trainer not found.",
                    "username", username
            ));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Trainer deleted successfully.",
                "username", username
        ));
    }
    

   
}




