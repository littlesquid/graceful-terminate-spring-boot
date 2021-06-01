package com.samuraiiway.gracefulterminate.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GracefulJob extends QuartzJobBean {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @SneakyThrows
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Executing Job");

        Thread.sleep(25000);

        rabbitTemplate.convertAndSend("common","finish.graceful.shutdown", Thread.currentThread().getName());

        log.info("Job completed");
    }
}
