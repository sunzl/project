package com.nami.service.topic.productor;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitTopicProductor {

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	//默认路由
	public void sendMessageTest() {

		String context = "此消息在，配置转发消息模式队列下， 有 TopicReceiver1 TopicReceiver2 TopicReceiver3 可以收到";

        String routeKey = "topic.message";

        String exchange = "topicExchange";

        context = "exchange:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        System.out.println("sendMessageTest : " + context);

        this.rabbitTemplate.convertAndSend(exchange, routeKey, context);
    }
	
	
	public void sendMessagesTest() {

		String context = "此消息在，配置转发消息模式队列下，有  TopicReceiver2 TopicReceiver3 可以收到";

        String routeKey = "topic.message.s";

        String exchange = "topicExchange";

        context = "exchange:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        System.out.println("sendMessagesTest : " + context);

        this.rabbitTemplate.convertAndSend(exchange, routeKey, context);
    }
	
	
	public void sendYmqTest() {

		String context = "此消息在，配置转发消息模式队列下，有 TopicReceiver3 可以收到";

        String routeKey = "topic.ymq";

        String exchange = "topicExchange";

        context = "exchange:" + exchange + ",routeKey:" + routeKey + ",context:" + context;

        System.out.println("sendYmqTest : " + context);

        this.rabbitTemplate.convertAndSend(exchange, routeKey, context);
    }
}
