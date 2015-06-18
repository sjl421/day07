package org.kafka;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

public class MessageRunner implements Runnable{
	private KafkaStream<byte[], byte[]> partition;
	
	MessageRunner(KafkaStream<byte[], byte[]> partition) {
		this.partition = partition;
	}
	
	public void run(){
		ConsumerIterator<byte[], byte[]> it = partition.iterator();
		while(it.hasNext()){
			MessageAndMetadata<byte[],byte[]> item = it.next();
			System.out.println("partiton:" + item.partition());
			System.out.println("offset:" + item.offset());
			System.out.println(new String(item.message()));//UTF-8
		}
	}
}

