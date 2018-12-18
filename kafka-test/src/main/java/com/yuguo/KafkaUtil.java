package com.yuguo;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class KafkaUtil {


    public static final Logger log = LoggerFactory.getLogger("sys");

    private KafkaUtil() {
    }


    private static KafkaUtil kafkaUtil = null;


    public static KafkaUtil getInstance() {

        if (kafkaUtil == null) {
            kafkaUtil = new KafkaUtil();

        }

        return kafkaUtil;
    }


    private Producer<String, String> getProduce() {
        Properties props = new Properties();
        props.put("bootstrap.servers", StaticConfig.kafka_servers);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 1024 * 10);//10M
        props.put("linger.ms", 10);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return new KafkaProducer<>(props);

    }

//
//    public void produce(String topic, List<String> message) throws InterruptedException {
//
//        Producer<String, String> producer = this.getProduce();
//
//
//        Date date = new Date();
//
//        for (String i : message) {
//            producer.send(new ProducerRecord<String, String>(topic, Long.toString(date.getTime()), i));
//        }
//
//        Thread.sleep(2000);
//
//        producer.flush();
//        producer.close();
//
//    }


    public void produce(String topic, List<JSONObject> message) throws IOException, InterruptedException {

        Producer<String, String> producer = this.getProduce();


        Date date = new Date();

        log.info("+++++++++++++ 开始发送 kafka 消息 topic : {}", topic);

        for (JSONObject i : message) {
            producer.send(new ProducerRecord<String, String>(topic, Long.toString(date.getTime()), i.toJSONString()));
        }


        log.info("+++++++++++++ produce success size " + message.size());
        Thread.sleep(2000);

        producer.flush();
        producer.close();


    }

    public void produce(String topic, String file) throws IOException, InterruptedException {


        List<String> strings = FileUtils.readLines(new File(file));

        List<JSONObject> message = new ArrayList<>();

        for (String item : strings) {
            JSONObject o = new JSONObject();
            o.put("message", item);
            o.put("type", "dbus_log");
            o.put("host", StaticConfig.host);

            message.add(o);
        }

        this.produce(topic, message);

    }

    public static void main(String[] args) throws IOException, InterruptedException {

//        List<String> strings = FileUtils.readLines(new File("/tmp/BJ_BJ_MOBILE_CNOS_EVERSEC_CXDR_objectType_0001_20171016235954_S1UHTTP_125211_0.txt"));
//
//        for (String i : strings) {
//            System.out.println(i);
//        }

        KafkaUtil util = new KafkaUtil();
        util.produce("test", "/tmp/BJ_BJ_MOBILE_CNOS_EVERSEC_CXDR_objectType_0001_20171016235954_S1UHTTP_125211_0.txt");

    }


}
