package com.nami.inter.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nami.inter.MQProducer;
@Service("mQProducer")
public class MQProducerImpl implements MQProducer{

	@Autowired
    private AmqpTemplate amqpTemplate;
	
    //private final static Logger LOGGER = Logger.getLogger(MQProducerImpl.class);
    /* (non-Javadoc)
     * @see com.stnts.tita.rm.api.mq.MQProducer#sendDataToQueue(java.lang.String, java.lang.Object)
     */
    @Override
    public void sendDataToQueue(String queueKey, Object object) {
        try {
        	//convertAndSend：将Java对象转换为消息发送到匹配Key的交换机中Exchange，
        	//由于配置了JSON转换，这里是将Java对象转换成JSON字符串的形式
            amqpTemplate.convertAndSend(queueKey, object);
        } catch (Exception e) {
            //LOGGER.error(e);
        }
    }

}
