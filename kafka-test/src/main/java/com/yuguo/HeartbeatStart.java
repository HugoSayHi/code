package com.yuguo;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class HeartbeatStart implements Runnable {

    public static final Logger log = LoggerFactory.getLogger("sys");

    private static String type = "dbus-heartbeat";

    String topic = null;

    public HeartbeatStart(String topic) {
        this.topic = topic;
    }


    @Override
    public void run() {

        while (true) {

            Date date = new Date();
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss").format(date);

            KafkaUtil kafkaUtil = KafkaUtil.getInstance();

            JSONObject o = new JSONObject();
            o.put("host", StaticConfig.host);
            o.put("@version", "1");
            o.put("clock", date.getTime() / 1000);
            o.put("@timestamp", timestamp);
            o.put("type", type);

            try {
                kafkaUtil.produce(topic, Arrays.asList(o));

                log.info("+++++++++++++++++++++++心跳发送成功");

                Thread.sleep(1000 * 60);

            } catch (IOException e) {
                log.error("",e);
            } catch (InterruptedException e) {
                log.error("",e);
            }

        }




    }


    public static void main(String[] args) {
//        System.out.println(new Date().getTime()/1000);

        new HeartbeatStart("xdr").run();

    }
}
