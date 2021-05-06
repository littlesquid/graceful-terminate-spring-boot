package com.samuraiiway.gracefulterminate.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

public class MyRabbitListenerContainerFactory extends SimpleRabbitListenerContainerFactory {

    private final long shutdownTimeout;

    public MyRabbitListenerContainerFactory(long shutdownTimeout) {
        super();
        this.shutdownTimeout = shutdownTimeout;
    }

    public SimpleMessageListenerContainer createContainerInstance() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setShutdownTimeout(shutdownTimeout);
        return container;
    }

}
