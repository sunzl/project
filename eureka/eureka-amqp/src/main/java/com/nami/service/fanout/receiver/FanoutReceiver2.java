package com.nami.service.fanout.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.souyunku.com")
public class FanoutReceiver2 {

	@RabbitHandler
    public void process(String message) {
        System.out.println("接收者 FanoutReceiver2," + message);
    }
}
