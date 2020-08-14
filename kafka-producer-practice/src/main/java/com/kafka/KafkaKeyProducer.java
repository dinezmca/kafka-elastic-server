package com.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaKeyProducer {
	
	@Autowired
	private KafkaTemplate<String, String> temple;
	
	public void pushMessage(String key, String value) {
		temple.send("Tamil", key, value);
	}

}
