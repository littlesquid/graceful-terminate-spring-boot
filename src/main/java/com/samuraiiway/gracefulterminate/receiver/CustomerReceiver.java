package com.samuraiiway.gracefulterminate.receiver;

import com.rabbitmq.client.Channel;
import com.samuraiiway.gracefulterminate.receiver.message.CustomerLoginMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomerReceiver {

    @RabbitListener(queues = "customer.login", containerFactory = "rabbitListenerContainerFactory")
    public void customerLoginReceiveMessage(
            CustomerLoginMessage message,
            Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long tag
    ) throws Exception {

        log.info("Receiving customer login message: [{}]", message);
        channel.basicAck(tag, false);

        Thread.sleep(20000);
        log.info("Done processing message");
    }
}
