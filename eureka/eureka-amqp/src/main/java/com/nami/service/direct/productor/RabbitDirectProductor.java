package com.nami.service.direct.productor;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitDirectProductor {

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	//默认路由
	public void sendHelloTest() {

        String context = "此消息在，默认的交换机模式队列下，有 helloReceiver 可以收到";

        String routeKey = "hello";

        context = "routeKey:" + routeKey + ",context:" + context;

        System.out.println("sendHelloTest : " + context);

        this.rabbitTemplate.convertAndSend(routeKey, context);
    }
	
	//指定一个路由
	public void sendDirectTest() {

        String context = "此消息在，默认的交换机模式队列下，有 DirectReceiver 可以收到";

        String routeKey = "direct";

        String exchange = "directExchange";

        context = "exchange:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        System.out.println("sendDirectTest : " + context);

        this.rabbitTemplate.convertAndSend(exchange, routeKey, context);
    }
}
