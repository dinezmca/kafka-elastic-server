package com.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kafka.entity.Employee;

@Service
public class EmployeeJsonProducer {

	Logger logger = LoggerFactory.getLogger(EmployeeJsonProducer.class);
	
	@Autowired
	private KafkaTemplate<String, String> template ;
	
	private ObjectMapper  mapper = new ObjectMapper();
	
	public void sendEmployee(Employee emp ) throws JsonProcessingException {
		
		String json = mapper.writeValueAsString(emp);
		
		logger.info("json : {}", emp.toString());
		
		template.send("t_employee",json);
	}
}
