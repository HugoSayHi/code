package com.yuguo;

import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class AppKafkaSendStart implements Runnable {

    public static final Logger log = LoggerFactory.getLogger("sys");

    @Override
    public void run() {


        String zooRoot = "/ftp/file";
        String zooDownloadRoot = "/ftp/download";

        ZooUtil zoo = ZooUtil.getInstance();

        KafkaUtil kafkaUtil = KafkaUtil.getInstance();

        String topic = StaticConfig.kafka_topic;


        try {

            while (true) {
                List<String> childNode = zoo.getChildNodeWithPath(zooRoot);

                List<String> toSend = new ArrayList<>();

                for (String item : childNode) {
                    List<String> childNode1 = zoo.getChildNodeWithPath(item);
                    if (childNode1.isEmpty()) {
                        toSend.add(item);
                    }
                }




                for (String it : toSend) {

                    byte[] data = zoo.getData(it);

                    String file = new String(data);

                    kafkaUtil.produce(topic, file);

                    zoo.createNode(it, Arrays.asList("success"));

                    log.info("====================send to kafka success========== " + it);
                }

                log.info("+++++++++++++AppKafkaSendStart fresh ");
                Thread.sleep(1000 * 10);
            }


        } catch (Exception e) {
            log.error("", e);
        }


//        try {
//            int i = 0;
//
//            while (true) {
//
//                List<String> childNode = zoo.getChildNode(zooRoot);
//                List<String> downloadNode = zoo.getChildNode(zooDownloadRoot);
//
//                List<String> toSend = new ArrayList<>();
//                for (String child : childNode) {
//                    if (!downloadNode.contains(child)) {
//                        toSend.add(child);
//                    }
//                }
//
//                for (String it : toSend) {
//
//                    byte[] data = zoo.getData(zooRoot + "/" + it);
//
//                    String file = new String(data);
//
//                    kafkaUtil.produce(topic, file);
//
//                    zoo.createNode(zooDownloadRoot, Arrays.asList(it));
//
//                    log.info("====================send to kafka success========== " + it);
//                }
//
//                i++;
//                log.info("+++++++++++++AppKafkaSendStart fresh " + i);
//                Thread.sleep(1000 * 10);
//            }
//        } catch (Exception e) {
//            log.error("", e);
//        }


    }

    public static void main(String[] args) {
        new AppKafkaSendStart().run();
    }


}
