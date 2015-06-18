package org.kafka;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

public class MyProducer {
	public static void main(String[] args) {
		Producer<String,String> inner=null;
		try{
			Properties properties = new Properties();
			properties.load(new FileInputStream(new File("producer.properties")));
			ProducerConfig config = new ProducerConfig(properties);
			inner = new Producer<String, String>(config);
			int i=0;
			while(true){
				KeyedMessage<String, String> km = new KeyedMessage<String, String>("test-topic","this is a sample" + i);
				inner.send(km);
				i++;
				Thread.sleep(2000);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(inner != null){
				inner.close();
			}
		}
	}
}
