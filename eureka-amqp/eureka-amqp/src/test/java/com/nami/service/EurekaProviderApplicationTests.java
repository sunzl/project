package com.nami.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.nami.service.direct.productor.RabbitDirectProductor;
import com.nami.service.fanout.productor.RabbitFanoutProductor;
import com.nami.service.topic.productor.RabbitTopicProductor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EurekaProviderApplicationTests {
	
	@Autowired
	RabbitDirectProductor rabbitDirectProductor;
	
	@Autowired
	RabbitFanoutProductor rabbitFanoutProductor;
	
	@Autowired
	RabbitTopicProductor rabbitTopicProductor;
	
	@Test
	public void directTest() {
		rabbitDirectProductor.sendHelloTest();
		System.out.println("------------------------");
		rabbitDirectProductor.sendDirectTest();
	}
	
	@Test
	public void fanoutTest() {
		rabbitFanoutProductor.sendPengleiTest();
		System.out.println("------------------------");
		//rabbitFanoutProductor.sendSouyunkuTest();
	}
	
	@Test
	public void topicTest() {
		rabbitTopicProductor.sendMessageTest();
		System.out.println("-----------1-------------");
		//rabbitTopicProductor.sendMessagesTest();
		//System.out.println("-----------2-------------");
		//rabbitTopicProductor.sendYmqTest();
	}

}
