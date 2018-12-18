package com.yuguo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class ZooWatch implements Watcher {
    @Override
    public void process(WatchedEvent watchedEvent) {


//        if (watchedEvent.getType().getIntValue() == Event.EventType.NodeDeleted.getIntValue()) {
//
//
//        }

//        String path = watchedEvent.getPath();
//        System.out.println(path);
//
//        System.out.println("=========" + watchedEvent.getType() + "---" + watchedEvent.getState().getIntValue());
    }
}
