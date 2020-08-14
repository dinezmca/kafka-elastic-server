package com.kafka;

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
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = "com.kafka")
//@EnableScheduling
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ProducerStart implements CommandLineRunner {
	Logger logger = LoggerFactory.getLogger(ProducerStart.class);
	
	@Autowired
	private KafkaKeyProducer producer;
	
	public static void main(String[] args) {
		SpringApplication.run(ProducerStart.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		for(int i =0 ; i<100; i++) {
			String key = "key " + (i%4);
			String data = "data" + i +" with key" + key;
			//logger.info("key------>"+key);
			producer.pushMessage(key, data);
		}
	}
}


//2020-08-06 15:02:01.474  INFO 10197 --- [           main] o.a.kafka.common.utils.AppInfoParser     : Kafka startTimeMs: 1596715321472
//2020-08-06 15:02:01.621  INFO 10197 --- [ad | producer-1] org.apache.kafka.clients.Metadata        : [Producer clientId=producer-1] Cluster ID: qNbBh9i7ReyS9aSHx2oMzg




