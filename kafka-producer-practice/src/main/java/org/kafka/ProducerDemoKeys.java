package org.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemoKeys {
	public static void main(String[] args) {

		final Logger logger = LoggerFactory.getLogger(ProducerDemoKeys.class);
		String bootStrapServer = "localhost:9092";

		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServer);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

		// Create the producer

		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

		for (int i = 0; i < 10; i++) {
			// Create producer record
			String key = "id " + Integer.toString(i);
			String topic = "Tamizh";

			ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, key, "Hello All");
			logger.info("Key: " + key); // log the key
			// id_0 is going to partition 1
			// id_1 partition 0
			// id_2 partition 2
			// id_3 partition 0
			// id_4 partition 2
			// id_5 partition 2ßßß
			// id_6 partition 0
			// id_7 partition 2
			// id_8 partition 1
			// id_9 partition 2
			producer.send(record, new Callback() {

				public void onCompletion(RecordMetadata recordMetadata, Exception e) {
					if (e == null) {
						
						// the record was successfully sent
						logger.info("Received new metadata. \n" + "Topic:" + recordMetadata.topic() + "\n"
								+ "Partition: " + recordMetadata.partition() + "\n" + "Offset: "
								+ recordMetadata.offset() + "\n" + "Timestamp: " + recordMetadata.timestamp());
					} else {
						logger.error("Error while producing", e);
					}
				}

			});
		}
		// flush data
		producer.flush();
		// flush and close producer
		producer.close();
	}
}
