package com.yuguo;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LogGen {


    public static final Logger log = LoggerFactory.getLogger(LogGen.class);

    public static void main(String[] args) throws InterruptedException {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        while (true) {
            log.info(sdf.format(new Date()));
            Thread.sleep(1000L);
        }

    }
}
