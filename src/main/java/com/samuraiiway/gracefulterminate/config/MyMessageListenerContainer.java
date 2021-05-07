package com.samuraiiway.gracefulterminate.config;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.exception.MessageRejectedWhileStoppingException;

public class MyMessageListenerContainer extends SimpleMessageListenerContainer {

    private boolean shuttingDown = false;

    public MyMessageListenerContainer() {}

    public boolean isShuttingDown() {
        return this.shuttingDown;
    }

    public void shutdown() {
        this.shuttingDown = true;
        super.shutdown();
    }

    public void executeListener(Channel channel, Object data) {
        if (this.isShuttingDown()) {
            this.logger.warn("Rejected new execution due to container is stopping");
            throw new MessageRejectedWhileStoppingException();
        }

        super.executeListener(channel, data);
    }
}
