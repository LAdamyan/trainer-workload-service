package com.trainer.workload.service;

import com.trainer.workload.model.ActionType;
import com.trainer.workload.model.Month;
import com.trainer.workload.model.TrainerWorkload;
import com.trainer.workload.model.TrainerWorkloadRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrainerWorkloadService {

    private final Map<String, TrainerWorkload> workloadData = new ConcurrentHashMap<>();

    public void processWorkload(TrainerWorkloadRequest request) {
        String transactionId = MDC.get("transactionId");
        log.info("[{}] Processing workload request: {}", transactionId, request);

        workloadData.computeIfAbsent(request.getUsername(), k ->
                new TrainerWorkload(request.getUsername(), request.getFirstName(), request.getLastName(), request.isActive())
        );

        TrainerWorkload trainer = workloadData.get(request.getUsername());
        int year = request.getTrainingDate().getYear();
        Month month = Month.valueOf(request.getTrainingDate().getMonth().name());

        trainer.getWorkload().computeIfAbsent(year, y -> new ConcurrentHashMap<>());
        trainer.getWorkload().get(year).merge(month, request.getTrainingDuration(), Integer::sum);

        if (request.getActionType() == ActionType.DELETE) {
            trainer.getWorkload().get(year).compute(month, (k, currentDuration) ->
                    currentDuration == null ? 0 : Math.max(0, currentDuration - request.getTrainingDuration())
            );
        }

        log.info("[{}] Updated workload data: {}", transactionId, workloadData);
    }


    public List<TrainerWorkload> getAllWorkloadData() {
        String transactionId = MDC.get("transactionId");
        log.info("[{}] Fetching all workload data", transactionId);

        return workloadData.values().stream()
                .map(TrainerWorkload::fromEntity)
                .collect(Collectors.toList());
    }


    public TrainerWorkload getTrainerWorkload(String username) {
        String transactionId = MDC.get("transactionId");
        log.info("[{}] Fetching workload for user: {}", transactionId, username);

        TrainerWorkload trainer = workloadData.get(username);
        if (trainer == null) {
            throw new NoSuchElementException("Trainer not found.");
        }
        return TrainerWorkload.fromEntity(trainer);
    }

    public boolean deleteTrainer(String username) {
        return workloadData.remove(username) != null;
    }

    public int getMonthlyTrainingHours(String username, int year, Month month) {
        TrainerWorkload trainer = workloadData.get(username);
        if (trainer == null || !trainer.getWorkload().containsKey(year) || !trainer.getWorkload().get(year).containsKey(month)) {
            return 0;
        }
        return trainer.getWorkload().get(year).get(month);
    }




}