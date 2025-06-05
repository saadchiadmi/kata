package com.example.kata.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class BatchScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchScheduler.class);

    private final JobLauncher jobLauncher;
    private final Job runJob;

    @Autowired
    public BatchScheduler(JobLauncher jobLauncher, Job runJob) {
        this.jobLauncher = jobLauncher;
        this.runJob = runJob;
    }

    //launch the batch every 5min
    @Scheduled(fixedRate = 300000)
    public void runBatchJob() {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(runJob, jobParameters);
            LOGGER.info("Batch job has been started successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error starting batch job: " + e.getMessage());
        }
    }
}
