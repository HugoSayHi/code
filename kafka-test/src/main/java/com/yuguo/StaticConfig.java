package com.yuguo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class StaticConfig {

    public static final Logger log = LoggerFactory.getLogger("sys");

    public static String host;


    public static String ftp_host;
    public static int ftp_port;
    public static String ftp_username;
    public static String ftp_password;
    public static String ftp_dir;

    public static String kafka_topic;
    public static String kafka_servers;

    public static String zoo_servers;






    private static String getPath() {

        String path = StaticConfig.class.getProtectionDomain().getCodeSource().getLocation().getPath();



        int startIndex = 0;
        int lastIndex = path.lastIndexOf(File.separator) + 1;

        return path.substring(startIndex, lastIndex);
    }

    static {

        Properties prop = new Properties();
        try {

            String fileName = "config/config.properties";

            String path = getPath();

            log.info("================ StaticConfig :" + path);

            File f = new File(path + fileName);

            if (f.exists()){
                prop.load(new FileInputStream(f));

            }else {
                prop.load(StaticConfig.class.getClassLoader().getResourceAsStream(fileName));
            }

            host = prop.getProperty("host");

            ftp_host = prop.getProperty("ftp_host");
            ftp_port = Integer.parseInt(prop.getProperty("ftp_port", "21"));
            ftp_username = prop.getProperty("ftp_username");
            ftp_password = prop.getProperty("ftp_password");
            ftp_dir = prop.getProperty("ftp_dir");


            kafka_topic = prop.getProperty("kafka_topic");
            kafka_servers = prop.getProperty("kafka_servers");

            zoo_servers = prop.getProperty("zoo_servers");


        } catch (IOException e) {
            log.error("", e);
        }


    }




//    public static void main(String[] args) {
//
//
//
//        System.out.println(KAFKA_TOPIC);
//
//    }



}
