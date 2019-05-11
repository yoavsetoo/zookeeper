package com.example.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.example.zookeeper.ZookeeperApplication.CHILD_PREFIX;
import static com.example.zookeeper.ZookeeperApplication.PARENT_PATH;


public class LeaderCheck {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaderCheck.class);
    private final ZooKeeper zooKeeper;

    public LeaderCheck(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }

    public void check(String myPath) throws KeeperException, InterruptedException {
        List<String> children = zooKeeper.getChildren(PARENT_PATH, false);
        String smallest = findSmallest(children, CHILD_PREFIX);
        String me = myPath.substring(PARENT_PATH.length()+1, myPath.length());
        if (me.equals(smallest)) {
            LOGGER.info("I am the leader. me={}, children={}",me,children);
        }

        Collections.sort(children,new NodeIndexComparator(CHILD_PREFIX));

        for (int i = 1; i < children.size(); i++) {
            String prev = children.get(i - 1);
            String current = children.get(i);
            LOGGER.info("prev={}, current={}, me={}", prev, current, me);
            if (current.equals(me)) {
                String prevPath = PARENT_PATH+"/"+prev;
                LOGGER.info("listen to {}",prevPath);
                zooKeeper.getData(prevPath, new NodeWatcher(this,myPath) , null);
            }
        }
    }


    private String findSmallest(List<String> children, String prefix) {
        String min = Collections.min(children, new NodeIndexComparator(prefix));

        return min;
    }

}
