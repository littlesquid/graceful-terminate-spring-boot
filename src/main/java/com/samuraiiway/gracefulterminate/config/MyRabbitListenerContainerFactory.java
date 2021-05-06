package com.samuraiiway.gracefulterminate.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.AbstractMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

public class MyRabbitListenerContainerFactory extends SimpleRabbitListenerContainerFactory {

    private long shutdownTimeout = AbstractMessageListenerContainer.DEFAULT_SHUTDOWN_TIMEOUT;

    public MyRabbitListenerContainerFactory() {
    }

    public SimpleMessageListenerContainer createContainerInstance() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setShutdownTimeout(shutdownTimeout);
        return container;
    }

    public void setShutdownTimeout(long shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }
}
