package com.yuguo;

import sun.nio.ch.ThreadPool;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class AppStarter {


    public static void main(String[] args) throws IOException {


        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(new AppFtpDownloadStart());
        executorService.execute(new AppKafkaSendStart());
        executorService.execute(new HeartbeatStart(StaticConfig.kafka_topic));


//        File f = new File("/tmp/BJ_BJ_MOBILE_CNOS_EVERSEC_CXDR_objectType_0001_20171016235954_S1UHTTP_125211_0.tar.gz");
//
//        UnTarGzUtil.unTarGz(f,"/tmp/");

    }
}
