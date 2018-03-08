package com.nami.service.fanout.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.penglei.net")
public class FanoutReceiver1 {

	@RabbitHandler
    public void process(String message) {
        System.out.println("接收者 FanoutReceiver1," + message);
    }
}
