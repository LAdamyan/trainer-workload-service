package com.trainer.workload.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Year {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int trainingYear;

    @ElementCollection
    @CollectionTable(name = "trainer_monthly_hours", joinColumns = @JoinColumn(name = "year_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "month")
    @Column(name = "training_summary")
    private Map<Month, TrainingSummary> monthlyTrainingHours = new HashMap<>();
}