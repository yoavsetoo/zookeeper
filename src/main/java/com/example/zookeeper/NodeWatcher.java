package com.example.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NodeWatcher implements Watcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeWatcher.class);

    private final String myPath;
    private final LeaderCheck leaderCheck;

    public NodeWatcher(LeaderCheck leaderCheck, String myPath) {
        this.myPath = myPath;
        this.leaderCheck = leaderCheck;
    }

    @Override
    public void process(WatchedEvent event) {
        String path1 = event.getPath();
        Event.KeeperState state = event.getState();
        LOGGER.info("process() me={}, monitoredPath={}, state={}", myPath, path1, state);

        try {
            this.leaderCheck.check(myPath);
        } catch (KeeperException e) {
            LOGGER.error("",e);
        } catch (InterruptedException e) {
            LOGGER.error("",e);
        }

    }
}
