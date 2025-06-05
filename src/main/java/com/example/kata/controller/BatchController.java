package com.example.kata.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchController.class);

    private final JobLauncher jobLauncher;
    private final Job runJob;

    @Autowired
    public BatchController(JobLauncher jobLauncher, Job runJob) {
        this.jobLauncher = jobLauncher;
        this.runJob = runJob;
    }

    @GetMapping("/run")
    public ResponseEntity<String> runBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(runJob, jobParameters);
            return ResponseEntity.accepted().body("Batch job has been started successfully.");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not start job: " + e.getMessage());
        }
    }
}
