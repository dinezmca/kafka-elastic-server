
package com.kafka;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import com.kafka.entity.Employee;
import com.kafka.producer.EmployeeJsonProducer;

@SpringBootApplication
@ComponentScan(basePackages = "com.kafka")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ProducerStart implements CommandLineRunner  {
	Logger logger = LoggerFactory.getLogger(ProducerStart.class);
	
	@Autowired
	private EmployeeJsonProducer producer;
	
	public static void main(String[] args) {
		SpringApplication.run(ProducerStart.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for(int i=0; i<5;  i++) {
			Employee emp = new Employee("emp--" +i ,"Employee" +i,  LocalDate.now());
			producer.sendEmployee(emp);		

		}
	}


}



