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
            log.info("Completed destroying");
        } catch (Exception ex) {}
    }
}
