package com.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
	Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
	
	@KafkaListener(topics = "Tamil", concurrency ="2")
	public void consume(ConsumerRecord<String, String> message) {
		logger.info("Key : {}, Partition : {}, Message : {}", message.key(), message.partition(), message.value());
	}
}
//Single consumer - 639 to 646 
//concurrency ="2" -648 to 650
//2020-08-06 15:11:59.625  INFO 10253 --- [ntainer#0-1-C-1] o.s.k.l.KafkaMessageListenerContainer    : test-consumer-group: partitions assigned: [Tamil-2]
//2020-08-06 15:11:59.625  INFO 10253 --- [ntainer#0-0-C-1] o.s.k.l.KafkaMessageListenerContainer    : test-consumer-group: partitions assigned: [Tamil-0, Tamil-1]
