package com.samuraiiway.gracefulterminate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@Configuration
public class AsyncConfiguration {

    @Value("${spring.lifecycle.timeout-per-shutdown-phase}")
    private Integer shutdownTimeout;

    @Bean
    public Executor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setAwaitTerminationMillis(shutdownTimeout);
        executor.setWaitForTasksToCompleteOnShutdown(true);

        return executor;
    }
}
