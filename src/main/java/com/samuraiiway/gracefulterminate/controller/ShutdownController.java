package com.samuraiiway.gracefulterminate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ShutdownController {

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @GetMapping("/shutdown")
    public ResponseEntity shutdown() {
        log.info("Shutting down service");

        log.info("Stop web server");

        log.info("Stop rabbit listener");
        rabbitListenerEndpointRegistry
                .getListenerContainers()
                .forEach(MessageListenerContainer::stop);

        log.info("Stop task executor");

        log.info("Completed preparing shutdown");
        return ResponseEntity.ok("");
    }

}
