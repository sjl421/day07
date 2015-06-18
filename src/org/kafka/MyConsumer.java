package org.kafka;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

public class MyConsumer {
	public static void main(String[] args) {
		ConsumerConfig config=null;
		ConsumerConnector connector=null;
		ExecutorService threadPool=null;
		try{
			Properties properties = new Properties();
			properties.load(new FileInputStream(new File("consumer.properties")));
			config = new ConsumerConfig(properties);
			connector = Consumer.createJavaConsumerConnector(config);
			Map<String,Integer> topics = new HashMap<String,Integer>();
			topics.put("test-topic", 2);//第二个参数是分区数partitionsNum
			Map<String, List<KafkaStream<byte[], byte[]>>> streams = connector.createMessageStreams(topics);
			List<KafkaStream<byte[], byte[]>> partitions = streams.get("test-topic");
			threadPool = Executors.newFixedThreadPool(2);
			for(final KafkaStream<byte[], byte[]> partition : partitions){
				threadPool.execute(new MessageRunner(partition));
			}
			System.in.read();
			threadPool.shutdownNow();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			connector.shutdown();
		}
	}
}
