package com.example.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@EnableScheduling
public class ZookeeperApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperApplication.class);

    @Value("${zookeeper.host:localhost:2181}")
    String zookeeperHost;

    @Value("${server.port}")
    int port;


    final CountDownLatch latch = new CountDownLatch(1);
    private String myPath;
    public static final String CHILD_PREFIX = "listener";// Assign parentPath to znode
    public static final String PARENT_PATH = "/darwin-listener-list";

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("run()");


        // znode parentPath

        // data in byte array
        byte[] data = "darwin listener listeners list".getBytes();

        try {
            ZooKeeperConnection zooKeeperConnection = new ZooKeeperConnection();
            ZooKeeper zooKeeper = zooKeeperConnection.connect(zookeeperHost);
            Stat stat = zooKeeper.exists(PARENT_PATH, true); // Stat checks the parentPath of the znode

            LeaderCheck leaderCheck = new LeaderCheck(zooKeeper);

            if (stat != null) {
                LOGGER.info("run() listener list exist.  the node version is {}", stat.getVersion());
            } else {
                LOGGER.info("run() listener list not exist. create node");
                zooKeeper.create(PARENT_PATH, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }

            //create the ephermal , sequential node
            myPath = zooKeeper.create(PARENT_PATH +"/" + CHILD_PREFIX
                    , "listener-ci".getBytes()
                    , ZooDefs.Ids.OPEN_ACL_UNSAFE
                    , CreateMode.EPHEMERAL_SEQUENTIAL);

            LOGGER.info("created {}", myPath);



            leaderCheck.check(myPath);


            latch.await();
            zooKeeperConnection.close();
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }


    private String findSmallest(List<String> children, String prefix) {
        String min = Collections.min(children, new NodeIndexComparator(prefix));

        return min;
    }


    public void stop() {
        latch.countDown();
    }
}
