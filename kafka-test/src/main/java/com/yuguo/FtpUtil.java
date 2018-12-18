package com.yuguo;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.ftp.FtpReplyCode;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

public class FtpUtil {
    public static final Logger log = LoggerFactory.getLogger("sys");

    private FtpUtil() {

    }

    private static FtpUtil ftp = null;

    public static FtpUtil getInstance() {
        if (ftp == null) {
            ftp = new FtpUtil();
        }
        return ftp;

    }

//    private String username = "ftpuser";
//    private String password = "123456";
//
//    private String hostname = "172.168.8.135";
//    private int port = 21;

    public FTPClient getClient() throws Exception {

        FTPClient client = new FTPClient();
        client.connect(StaticConfig.ftp_host, StaticConfig.ftp_port);


        client.setControlEncoding("UTF-8");

        int replyCode = client.getReplyCode();

        if (!FTPReply.isPositiveCompletion(replyCode)) {
            throw new Exception("");
        }

        if (!client.login(StaticConfig.ftp_username, StaticConfig.ftp_password)) {
            throw new Exception("");
        }

        return client;

    }

    public List<String> listFiles(String path) throws Exception {

        FTPClient client = this.getClient();

        String workDir = client.printWorkingDirectory();
        client.changeWorkingDirectory(workDir);
        FTPFile[] ftpFiles = null;

        if (path.isEmpty()) {
            ftpFiles = client.listFiles();
        } else {
            ftpFiles = client.listFiles(workDir+path);
        }

        List<String> files = new ArrayList<>();
        for (FTPFile f : ftpFiles) {
            files.add(f.getName());
        }
        client.disconnect();
        return files;
    }

    public Map<String,String> downloadFiles(String remotePath,String localPath, List<String> files) throws Exception {

        FTPClient client = this.getClient();

        String workDir = client.printWorkingDirectory();

        client.setControlEncoding("UTF-8"); // 中文支持
        client.setFileType(FTPClient.BINARY_FILE_TYPE);
        client.enterLocalPassiveMode();

        client.changeWorkingDirectory(workDir);

        Map<String,String> locals = new HashMap<>();
        String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        UnGzFile unGzFile = new UnGzFile();


        File local_ = new File(localPath);

        if (!local_.exists()) {
            local_.mkdirs();
        }

        for (String i : files) {
            String local = localPath + File.separator + date + "-" + i;
            String remote = workDir + "/" + remotePath + "/" + i;
            File f = new File(local);
            OutputStream out = new FileOutputStream(f);

            client.retrieveFile(remote, out);

            out.flush();
            out.close();

            log.info("文件下载成功" + remote);

            if (f.getName().endsWith("tar.gz")) {
                //解压
                List<String> strings = UnTarGzUtil.unTarGz(f, f.getParent()+File.separator+"untar");
                locals.put(i, strings.get(0));
            }else {
                locals.put(i, local);
            }
        }

        return locals;

    }


//    public void init() throws Exception {
//
//        FTPClient client = new FTPClient();
//        client.connect(hostname, port);
//
//
//        client.setControlEncoding("UTF-8");
//
//        int replyCode = client.getReplyCode();
//
//        if (!FTPReply.isPositiveCompletion(replyCode)) {
//            throw new Exception("");
//        }
//
//        if (!client.login(username, password)) {
//            throw new Exception("");
//        }
//
//        String workDir = client.printWorkingDirectory();
//        System.out.println(workDir);
//        boolean b = client.changeWorkingDirectory(workDir);
////        boolean tmp = client.makeDirectory("tmp");
//
//        InputStream in = new FileInputStream(new File("C:\\Users\\catt\\Desktop\\stdout"));
//
//        boolean b1 = client.storeFile(workDir + "/tmp/test.txt", in);
//
//
//        OutputStream out = new FileOutputStream(new File("/temp/test1.txt"));
//        boolean b2 = client.retrieveFile(workDir + "/tmp/test.txt", out);
//
//        out.flush();
//        out.close();
//
//
//        client.logout();·
//        client.disconnect();
//
//    }


    public static void main(String[] args) throws Exception {

//        FtpUtil.getInstance().init();


    }


}
