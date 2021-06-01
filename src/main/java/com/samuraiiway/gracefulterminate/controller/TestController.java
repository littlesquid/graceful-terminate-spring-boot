package com.samuraiiway.gracefulterminate.controller;

import com.samuraiiway.gracefulterminate.service.AsyncService;
import com.samuraiiway.gracefulterminate.service.GracefulJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

import static org.quartz.CronScheduleBuilder.cronSchedule;

@Slf4j
@RestController
public class TestController {

    @Autowired
    private AsyncService asyncService;

    @Autowired
    private Executor executor;

    @Autowired
    private Scheduler scheduler;

    @GetMapping("/test")
    public ResponseEntity test() throws Exception {
        log.info("Started test");
        Thread.sleep(10000);
        log.info("Completed test");
        return ResponseEntity.ok("test");
    }

    @GetMapping("/test/async")
    public ResponseEntity testAsync() throws Exception {
        asyncService.runAsyncTask();
        return ResponseEntity.ok("test async");
    }

    @GetMapping("/test/future")
    public ResponseEntity testFuture() {
        CompletableFuture.runAsync(() -> {
            try {
                log.info("Started completable future");
                Thread.sleep(10000);
                log.info("Completed completable future");
            } catch (Exception ex) {
                log.error("Future exception", ex);
            }
        }, executor);
        return ResponseEntity.ok("test future");
    }

    @GetMapping("/test/parallel")
    public ResponseEntity testDefaultParallel() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        numbers.parallelStream().forEach(i -> {
            try {
                log.info("Started default stream parallel: " + i);
                Thread.sleep(10000);
                log.info("Completed default stream parallel: " + i);
            } catch (Exception ex) {
                log.error("Default parallel exception", ex);
            }
        });
        return ResponseEntity.ok("test parallel");
    }

    @GetMapping("/test/parallel/fork-join-pool")
    public ResponseEntity testForkJoinPoolParallel() throws Exception {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        ForkJoinPool customThreadPool = new ForkJoinPool(10);
        customThreadPool.submit(() -> numbers.parallelStream().map(i -> {
            try {
                log.info("Started fork join pool stream parallel: " + i);
                Thread.sleep(10000);
                log.info("Completed fork join pool stream parallel: " + i);
            } catch (Exception ex) {
                log.error("Fork join pool parallel exception", ex);
            }

            return "" + i;
        }).count()).get();

        return ResponseEntity.ok("test fork join pool parallel");
    }

    @PostMapping("/schedule")
    public ResponseEntity schedule(@RequestBody String cronExpression) throws SchedulerException {
        log.info("creating job - trigger on : {}", cronExpression);
        JobDetail jobDetail = JobBuilder.newJob(GracefulJob.class)
                .withIdentity(UUID.randomUUID().toString(), "graceful-jobs")
                .withDescription("Test Graceful Job")
                .usingJobData(new JobDataMap())
                .storeDurably()
                .build();

        Trigger jobTrigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "graceful-triggers")
                .withDescription("Test Graceful Trigger")
                .withSchedule(cronSchedule(cronExpression))
                .build();

        scheduler.scheduleJob(jobDetail, jobTrigger);

        return ResponseEntity.ok("scheduled!");
    }
}
