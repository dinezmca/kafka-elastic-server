package com.kafka;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonParser;

public class ElasticSearchConsumer {
	
	final static Logger logger = LoggerFactory.getLogger(ElasticSearchConsumer.class);
	
	public static RestHighLevelClient createClient() {
		
		//Access tab from bonsai https://rrhem4kohh:wcxwax1d8i@kafka-cluster-7057938546.us-east-1.bonsaisearch.net:443
		
		String username="rrhem4kohh";
		String password ="wcxwax1d8i";
		String hostname="kafka-cluster-7057938546.us-east-1.bonsaisearch.net";
		RestHighLevelClient client = null;
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username,password));
		
		RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(hostname,443, "https" )).setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
			
			@Override
			public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
				// TODO Auto-generated method stub
				return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
			}
		});
		 client = new RestHighLevelClient(restClientBuilder);
	
		return client;
		
	}
	 private static JsonParser jsonParser = new JsonParser();

	    private static String extractIdFromTweet(String tweetJson){
	        // gson library
	        return jsonParser.parse(tweetJson)
	                .getAsJsonObject()
	                .get("_id")
	                .getAsString();
	    }
	public static void main(String[] args) throws IOException {
		
		RestHighLevelClient client  = createClient();
		
		KafkaConsumer<String, String> consumer = createConsumer("Tamil");
		
		BulkRequest bulkRequest = new BulkRequest();
		
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			 Integer recordCount = records.count();
			logger.info("Received" + recordCount + "Records");
			for (ConsumerRecord<String, String> record : records) {

				try {
					//String id = extractIdFromTweet(record.value());

					// IndexRequest indexRequest = new IndexRequest("twitter","tweets", id
					// ).source(record.value(), XContentType.JSON);

					IndexRequest indexRequest = new IndexRequest("tweets").source(record.value(), XContentType.JSON);
							//.id(id); // this is to make our consumer idempotent

					bulkRequest.add(indexRequest);

				} catch (Exception e) {
					e.printStackTrace();
				}

				consumer.commitSync();
			}
			logger.info("Offsets have been committed");
			 if (recordCount > 0) {
	                BulkResponse bulkItemResponses = client.bulk(bulkRequest, RequestOptions.DEFAULT);
	                logger.info("Committing offsets...");
	                consumer.commitSync();
	                logger.info("Offsets have been committed");
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	            }
		}		
		
	}

	 public static KafkaConsumer<String, String> createConsumer(String topic){

	        String bootstrapServers = "127.0.0.1:9092";
	        String groupId = "kafka-demo-elasticsearch";

	        // create consumer configs
	        Properties properties = new Properties();
	        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
	        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
	        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
	        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
	        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
	        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); // disable auto commit of offsets
	        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10"); // disable auto commit of offsets

	        // create consumer
	        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
	        consumer.subscribe(Arrays.asList(topic));

	        return consumer;

	    }

}