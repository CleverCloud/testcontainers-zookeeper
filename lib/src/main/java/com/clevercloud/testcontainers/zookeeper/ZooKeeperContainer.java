/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.clevercloud.testcontainers.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;

public class ZooKeeperContainer extends GenericContainer<ZooKeeperContainer> {
    public static final String DEFAULT_TAG = "3.7";
    public static final String DEFAULT_IMAGE = "zookeeper";
    public static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName
            .parse(DEFAULT_IMAGE)
            .withTag(DEFAULT_TAG);

    public static final Integer DEFAULT_CLIENT_PORT = 2181;
    public static final Integer DEFAULT_ADMIN_PORT = 8080;
    public static final Duration DEFAULT_STARTUP_TIMEOUT = Duration.ofSeconds(60);

    private final Logger logger = LoggerFactory.getLogger(ZooKeeperContainer.class);

    public ZooKeeperContainer() {
        this(DEFAULT_IMAGE_NAME, DEFAULT_STARTUP_TIMEOUT);
    }

    public ZooKeeperContainer(final DockerImageName dockerImageName, final Duration startUpTimeOut) {
        super(dockerImageName);

        withExposedPorts(DEFAULT_CLIENT_PORT, DEFAULT_ADMIN_PORT);
        waitingFor(Wait.forListeningPort().withStartupTimeout(startUpTimeOut));
    }
}
