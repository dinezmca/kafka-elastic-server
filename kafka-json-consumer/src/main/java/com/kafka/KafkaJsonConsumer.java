package com.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.entity.Employee;

@Service
public class KafkaJsonConsumer {
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private static final Logger logger = LoggerFactory.getLogger(KafkaJsonConsumer.class);
	
	@KafkaListener(topics = "t_employee")
	public void consume(String message) throws JsonMappingException, JsonProcessingException{
		Employee  emp = mapper.readValue(message, Employee.class);		
		logger.info("Employee {}", emp);
		
	}
}
