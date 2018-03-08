
package com.nami.test;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.nami.inter.MQProducer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@RunWith(value=SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:application-context.xml"})
public class TestMq {
	
	@Autowired
    MQProducer mqProducer;

    final String queue_key = "test_queue_key";

    @Test
    public void send(){
        Map<String,Object> msg = new HashMap<>();
        msg.put("data","hello,rabbmitmq!");
        mqProducer.sendDataToQueue(queue_key,msg);
    }

    public static void main1(String[] args) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUri("amqp://test:test123@localhost:5672");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		System.out.println(channel.getChannelNumber());
	}
    public static void main(String[] args) throws InterruptedException {  
        
        AbstractApplicationContext ctx =  new ClassPathXmlApplicationContext("application-context.xml");  
//      AmqpTemplate  template = (AmqpTemplate) ctx.getBean("amqpTemplate");  
        MQProducer mqProducer = (MQProducer) ctx.getBean("mQProducer");  
        mqProducer.sendDataToQueue("test_queue_key", "sdfgsdf");
  }  
}
