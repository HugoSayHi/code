package com.yuguo;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class KafkaProduceTest {


    public static void main(String[] args) throws InterruptedException {


//        Properties props = new Properties();
//        props.put("bootstrap.servers", "172.168.8.132:9092,172.168.8.133:9092,172.168.8.134:9092");
//        props.put("acks", "all");
//        props.put("retries", 0);
//        props.put("batch.size", 16384);
//        props.put("linger.ms", 1);
//        props.put("buffer.memory", 33554432);
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//
//        Producer<String, String> producer = new KafkaProducer<>(props);
//        int i = 1;
//        while (true){
//
//            producer.send(new ProducerRecord<String, String>("test", Integer.toString(i), Integer.toString(i)));
//            System.out.println("+++");
////            producer.close();
//            Thread.sleep(2000);
//            i++;
//
//        }




    }


}
