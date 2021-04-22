package com.samuraiiway.gracefulterminate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AsyncService {

    @Async
    public void runAsyncTask() throws Exception {
        log.info("Started running async task");
        Thread.sleep(10000);
        log.info("Completed async task");
    }
}
