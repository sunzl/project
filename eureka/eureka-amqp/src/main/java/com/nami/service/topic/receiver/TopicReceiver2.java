package com.nami.service.topic.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.message.s")
public class TopicReceiver2 {

	@RabbitHandler
    public void process(String message) {
        System.out.println("接收者 TopicReceiver2," + message);
    }
}
