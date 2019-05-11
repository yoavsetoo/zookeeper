package com.example.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LeaderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaderController.class);

    @Autowired
    ZookeeperApplication zookeeperApplication;


    @GetMapping("/start")
    public void start() {
        LOGGER.info("start()");

    }

    @GetMapping("/stop")
    public void stop() {
        LOGGER.info("stop()");
        zookeeperApplication.stop();

    }

}
