package com.samuraiiway.gracefulterminate.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Slf4j
@Component
public class ShutdownHook {

    @PreDestroy
    public void destroy() {
        try {
            log.info("Started destroying");
            Thread.sleep(200);
            log.info("Completed destroying");
        } catch (Exception ex) {}
    }
}
