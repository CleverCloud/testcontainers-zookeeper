package com.clevercloud.testcontainers.zookeeper;

import org.apache.zookeeper.*;
import org.junit.Test;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;

@Testcontainers
public class ZooKeeperContainerTest {
    @Container
    public final ZooKeeperContainer container = new ZooKeeperContainer();

    @Test
    public void writeFollowedByARead() throws InterruptedException, KeeperException, IOException {
        container.start();

        final CountDownLatch connectionLatch = new CountDownLatch(1);
        final ZooKeeper zk = new ZooKeeper(container.getHost() + ":" + container.getMappedPort(ZooKeeperContainer.DEFAULT_CLIENT_PORT), 5000, event -> {
            if (event.getState() == Watcher.Event.KeeperState.SyncConnected) {
                connectionLatch.countDown();
            }
        });

        connectionLatch.await();

        zk.create("/test", "test".getBytes(StandardCharsets.UTF_8), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zk.getData("/test", false, zk.exists("/test", true));

        zk.close();
    }
}
