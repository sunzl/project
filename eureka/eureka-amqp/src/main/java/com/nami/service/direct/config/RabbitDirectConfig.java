package com.nami.service.direct.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitDirectConfig {

	//绑定一个key "" 即默认路由，当消息匹配到就会放到这个队列中
	@Bean
    public Queue helloQueue() {
        return new Queue("hello");
    }

    @Bean
    public Queue directQueue() {
        return new Queue("direct");
    }

    //-------------------配置默认的交换机模式，可以不需要配置以下-----------------------------------
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    //绑定一个key "direct"，当消息匹配到就会放到这个队列中
    @Bean
    Binding bindingExchangeDirectQueue(Queue directQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(directQueue).to(directExchange).with("direct");
    }
}
