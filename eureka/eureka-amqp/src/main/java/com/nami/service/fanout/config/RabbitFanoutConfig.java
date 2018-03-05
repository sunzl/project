package com.nami.service.fanout.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
/**
 * fanout类型的路由与路由的key无关
 * 队列绑定到交换机上有关
 */
@Component
public class RabbitFanoutConfig {

	final static String PENGLEI = "fanout.penglei.net";

    final static String SOUYUNKU = "fanout.souyunku.com";
    
	@Bean
    public Queue queuePenglei() {
        return new Queue(PENGLEI);
    }

    @Bean
    public Queue queueSouyunku() {
        return new Queue(SOUYUNKU);
    }


    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    Binding bindingExchangeQueuePenglei(Queue queuePenglei, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queuePenglei).to(fanoutExchange);
    }
    
    @Bean
    Binding bindingExchangeQueueSouyunku(Queue queueSouyunku, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queueSouyunku).to(fanoutExchange);
    }
    
}
