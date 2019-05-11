package com.example.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


@Configuration
public class LeaderConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaderConfig.class);

    @Value("${server.port}")
    int port;

    final CountDownLatch connectedSignal = new CountDownLatch(1);




/*
    @Bean
    public LeaderSelector leaderSelector() {
        int sleepMsBetweenRetries = 100;
        int maxRetries = 3;
        RetryPolicy retryPolicy = new RetryNTimes(
                maxRetries, sleepMsBetweenRetries);

        CuratorFramework client = CuratorFrameworkFactory
                .newClient("127.0.0.1:2181", retryPolicy);
        client.start();

        CuratorZookeeperClient zookeeperClient = client.getZookeeperClient();
        try {
            ZooKeeper zooKeeper = zookeeperClient.getZooKeeper();

            Stat exists = zooKeeper.exists("/test", true);
            zooKeeper.create("/test","hello".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
        } catch (Exception e) {
            LOGGER.error("",e);
        }



        LeaderSelector leaderSelector = new LeaderSelector(client,
                "/mutex/select/leader/for/job/A",new LeaderSelectorListenerImpl(port));





        // for most cases you will want your instance to requeue when it relinquishes leadership
        leaderSelector.autoRequeue();

        return leaderSelector;

    }
*/
}
