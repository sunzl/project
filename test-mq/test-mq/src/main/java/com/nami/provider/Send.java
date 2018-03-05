package com.nami.provider;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Send {

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
            String message = "hello world";  
            /**
             * 发送exchange默认类型 direct，根据route key来发往队列
             */
            //channel.queueDeclare(QUEUE_NAME, false, false, false, null);  
            //channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));  
            /**
             * exchange 是 fanout类型即广播类型，会发往所有监听队列消费者,
             * 此时 route key无意义
             */
            channel.exchangeDeclare(QUEUE_NAME, "fanout", true, false, null);
            channel.basicPublish(QUEUE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println("已经发送消息....."+message);  
        } catch (IOException | java.util.concurrent.TimeoutException e) {  
            e.printStackTrace();  
        }finally{  
            try {  
                //关闭资源  
                try {
					channel.close();
				} catch (java.util.concurrent.TimeoutException e) {
					e.printStackTrace();
				}  
                connection.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
	}
}
