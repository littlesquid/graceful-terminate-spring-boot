package com.samuraiiway.gracefulterminate.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Slf4j
@Configuration
public class ShutdownConfiguration {

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @EventListener(classes = { ContextRefreshedEvent.class })
    public void handleContextRefreshEvent() {
        log.info("Started handle context refresh event");

        log.info("Setup rabbit listener timeout");
        rabbitListenerEndpointRegistry
                .getListenerContainers()
                .forEach(container -> {
                    SimpleMessageListenerContainer simpleContainer = (SimpleMessageListenerContainer) container;
                    simpleContainer.setShutdownTimeout(30000);
                });

        log.info("Completed handle context refresh event");
    }
}
