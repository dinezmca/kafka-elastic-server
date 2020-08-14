package com.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {
	
	@Autowired
	private KafkaTemplate< String, String> template ;
	
	int i =0 ;
	@Scheduled(fixedRate = 1000)
	public void sendMessage() {
		i++;
		template.send("Tamil", "Riyadh-----"+i);
	}
}
