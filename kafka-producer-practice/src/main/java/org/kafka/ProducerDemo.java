package org.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerDemo {
	public static void main(String[] args) {
		
		String bootStrapServer = "localhost:9092";
		
		String topic ="Tamizh";
		 // create Producer properties
		
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


		//Create the producer
		
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
		
		//Create producer record
		
		ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, "Hello All");
		
		producer.send(record);
        // flush data
        producer.flush();
        // flush and close producer
        producer.close();

		
	}

}
