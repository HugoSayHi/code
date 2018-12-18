package com.yuguo;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ZooUtil {
    public static final Logger log = LoggerFactory.getLogger("sys");


    private String zooServer = StaticConfig.zoo_servers;


    private ZooUtil() {
    }


    private static ZooUtil zooUtil = null;

    public static ZooUtil getInstance() {
        if (zooUtil == null) {
            zooUtil = new ZooUtil();
        }
        return zooUtil;
    }


    private int timeout = 1000 * 60 * 5;


    public List<String> getChildNodeWithPath(String path) throws Exception {


        List<String> children = new ArrayList<>();
        for (String item : this.getChildNode(path)) {
            children.add(path + "/" + item);
        }
        return children;
    }

    public List<String> getChildNode(String path) throws Exception {

        ZooKeeper zoo = new ZooKeeper(zooServer, timeout, new ZooWatch());

        try {

            if (zoo.exists(path, false) == null) {
                zoo.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            List<String> children = zoo.getChildren(path, false);


            return children;

        } catch (Exception e) {
            log.error("", e);
            throw new Exception(e);
        } finally {
            if (zoo != null) {
                zoo.close();
            }
        }


    }

    public void createNode(String rootPath, List<String> nodeNames) throws Exception {
        this.createNode(rootPath, nodeNames, null);
    }

    public void createNode(String rootPath, List<String> nodeNames, Map<String, String> data) throws Exception {


        ZooKeeper zoo = new ZooKeeper(zooServer, timeout, new ZooWatch());

        try {


            if (zoo.exists(rootPath, false) == null) {
                String s = zoo.create(rootPath, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            for (String item : nodeNames) {
                String node_ = rootPath + "/" + item;
                if (zoo.exists(node_, true) == null) {
                    zoo.create(node_, data == null || data.get(item) == null ? "".getBytes() : data.get(item).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                    log.info("创建新节点成功" + node_);
                }
            }


        } catch (Exception e) {
            log.error("", e);
            throw new Exception(e);

        } finally {
            if (zoo != null) {
                zoo.close();
            }
        }
    }


    public Stat setData(String path, byte[] data) throws Exception {

        ZooKeeper zoo = new ZooKeeper(zooServer, timeout, new ZooWatch());

        try {

            Stat stat = zoo.exists(path, false);
            if (stat == null) {
                zoo.create(path, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                return zoo.setData(path, data, 0);
            }

            return zoo.setData(path, data, stat.getVersion());

        } catch (Exception e) {
            log.error("", e);
            throw new Exception(e);
        } finally {
            if (zoo != null) {
                zoo.close();
            }
        }
    }


    public byte[] getData(String path) throws Exception {

        ZooKeeper zoo = new ZooKeeper(zooServer, timeout, new ZooWatch());

        try {
            if (zoo.exists(path, false) != null) {
                return zoo.getData(path, false, null);
            }

            return null;

        } catch (Exception e) {
            log.error("", e);
            throw new Exception(e);
        } finally {
            if (zoo != null) {
                zoo.close();
            }
        }

    }

    public static void main(String[] args) throws Exception {

        ZooUtil zoo = new ZooUtil();
//        zoo.createNode("/ftp/file", Arrays.asList("test3", "test4"));
//        List<String> childNode = zoo.getChildNode("/ftp/file");
//        for (String i : childNode) {
//            System.out.println(i);
//        }

//        Stat stat = zoo.setData("/ftp/file/test.txt", "hello world".getBytes());
//
//        byte[] data = zoo.getData("/ftp/file/test.txt");
//
//        System.out.println(new String(data));

    }


}
