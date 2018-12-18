package com.yuguo;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UnGzFile extends UnZipUtil {


    public static final Logger log = LoggerFactory.getLogger("sys");

    public static final String TEMP_PATH = "temp";
    public static final String RESULT_PATH = "result";

    @Override
    public List<String> unZipFile(String path, String fileName) throws Exception {

        String zipFileName = path + File.separator + fileName;
        String tempPath = path + File.separator + TEMP_PATH;
        String resultPath = path + File.separator + RESULT_PATH;


        try {
            File file = new File(zipFileName);

            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

            String tarFileName = fileName.substring(0, file.getName().lastIndexOf("."));
            String tarFile = tempPath + File.separator + tarFileName;
            File tarFile_ = new File(tarFile);

            if (!tarFile_.exists()) {
                tarFile_.getParentFile().mkdir();
            }


            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tarFile));

            GzipCompressorInputStream gcis = new GzipCompressorInputStream(bis);
            byte[] buffer1 = new byte[BYTE_SIZE];
            int read1 = -1;
            while ((read1 = gcis.read(buffer1)) != -1) {
                bos.write(buffer1, 0, read1);
            }

            gcis.close();
            bis.close();
            bos.close();

            //解压tar

            TarArchiveInputStream tais = new TarArchiveInputStream(new FileInputStream(new File(tarFile)));

            List<String> files = new ArrayList<>();

            TarArchiveEntry tarArchiveEntry = null;
            while ((tarArchiveEntry = tais.getNextTarEntry()) != null) {
                String name = tarArchiveEntry.getName();
                File resultFile = new File(resultPath, name);
                if (!resultFile.getParentFile().exists()) {
                    resultFile.getParentFile().mkdirs();
                }
                BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream(resultFile));
                int read2 = -1;
                byte[] buffer2 = new byte[BYTE_SIZE];
                while ((read2 = tais.read(buffer2)) != -1) {
                    bos2.write(buffer2, 0, read2);
                }

                files.add(resultPath + File.separator + name);

                bos2.close();
            }

            tais.close();

            return files;


        } catch (Exception e) {
            log.error("", e);
            throw new Exception(e);
        }


    }

    public static void main(String[] args) throws Exception {

//        log.info("===========");
//        log.error("+++++++++++++");
//
//        UnGzFile un = new UnGzFile();
//        List<String> strings = un.unZipFile("C:\\temp", "BJ_BJ_MOBILE_CNOS_EVERSEC_CXDR_objectType_0001_20171016235954_S1UHTTP_125211_0.tar.gz");
//
//        System.out.println(strings.toString());

//        un.unZipFile("C:\\temp", "dbus-flume.tar.gz");

        Logger l = LoggerFactory.getLogger("test");
        l.info("===============");


    }


}
