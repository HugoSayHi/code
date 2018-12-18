package com.yuguo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppFtpDownloadStart implements Runnable {

    public static final Logger log = LoggerFactory.getLogger("sys");

    @Override
    public void run() {

        String localStorePath = "/tmp/ftp_download";

//        String ftpPath = "/tmp";

        String zooRoot = "/ftp/file";

        FtpUtil ftpUtil = FtpUtil.getInstance();

        ZooUtil zoo = ZooUtil.getInstance();


        try {

            while (true) {

                List<String> files = ftpUtil.listFiles(StaticConfig.ftp_dir);
                List<String> childNode = zoo.getChildNode(zooRoot);

                List<String> toDownlaod = new ArrayList<>();
                Map<String, String> fileToZooNode = new HashMap<>();
                for (String file : files) {
                    if (!childNode.contains(file)) {
                        toDownlaod.add(file);
                        fileToZooNode.put(file, zooRoot + "/" + file);
                    }
                }

                Map<String, String> downloadFiles = ftpUtil.downloadFiles(StaticConfig.ftp_dir, localStorePath, toDownlaod);

                zoo.createNode(zooRoot, toDownlaod, downloadFiles);

                log.info("+++++++++++++AppFtpDownloadStart refresh ");
                Thread.sleep(1000 * 10);
            }
        } catch (Exception e) {
            log.error("", e);
        }

    }

    public static void main(String[] args) {
        new AppFtpDownloadStart().run();

    }


}
