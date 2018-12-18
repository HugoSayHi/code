package com.yuguo;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class KafkaConsumerTest {


    public static void main(String[] args) {


        String topic = "test_flume";

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.127.161:9092,192.168.127.162:9092,192.168.127.163:9092");
        props.put("group.id", "test");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

//        consumer.subscribe(Arrays.asList("test"));


        TopicPartition partition = new TopicPartition(topic, 0);

        consumer.assign(Arrays.asList(partition));

        Map<TopicPartition, Long> beginningOffsets = consumer.beginningOffsets(Arrays.asList(partition));
        Map<TopicPartition, Long> endOffsets = consumer.endOffsets(Arrays.asList(partition));

        Set<TopicPartition> assignment = consumer.assignment();

        consumer.seek(partition, endOffsets.get(partition));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }

            consumer.commitAsync(new OffsetCommitCallback() {
                @Override
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> map, Exception e) {

//                    if (e != null) {
//                        e.printStackTrace();
//
//                    }
//                    for (TopicPartition i : map.keySet()) {
//
//                        OffsetAndMetadata offsetAndMetadata = map.get(i);
//
//                        System.out.println("消费成功 topic=" + i.topic() + " partition=" + i.partition() + " metadata=" + offsetAndMetadata.metadata() + " offset=" + offsetAndMetadata.offset());
//
//
//                    }


                }
            });

        }

    }

}
