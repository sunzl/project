package com.nami.consumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Recive {

	private final static String QUEUE_NAME = "hello";
    static boolean isBreak =false;
    public static void main(String[] args) {
		ConnectionFactory factory = null;  
        Connection connection = null;  
        Channel channel = null;  
          
        try {  
            factory = new ConnectionFactory();  
            factory.setHost("localhost");  
            factory.setPort(5672);
    		factory.setUsername("admin");
    		factory.setPassword("admin123");
    		factory.setVirtualHost("/");
            connection = factory.newConnection();  
            channel = connection.createChannel();  
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);  
            //channel.basicQos(1);//每次从队列获取的数量
            Consumer consumer = new DefaultConsumer(channel){  
                @Override  
                public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,  
                        byte[] body) throws IOException {  
                    String message = new String(body, "UTF-8");  
                    System.out.println("收到消息....."+message);  
                }
            };  
            channel.basicConsume(QUEUE_NAME, true,consumer);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (TimeoutException e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                //关闭资源  
                try {
					channel.close();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}  
                connection.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }   
	}
}
