package com.simonjamesrowe.component.test.redis;

import com.simonjamesrowe.component.test.containers.ContainerConfigurationUtils;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Runs test containers for Redis
 * <p>
 * It will keep the containers running across multiple test classes unlike using the original
 * implementation which uses @ClassRule
 */
@Slf4j
public class RedisTestContainersExtension extends GenericContainer implements BeforeAllCallback {

    private static final String REDIS_PASSWORD = UUID.randomUUID().toString();
    private static RedisTestContainersExtension container;

    public RedisTestContainersExtension() {
        super("redis:6-alpine");
        withExposedPorts(6379);
        withCommand("redis-server", "--bind", "0.0.0.0", "--requirepass", REDIS_PASSWORD);
        setWaitStrategy(Wait.forListeningPort());
        ContainerConfigurationUtils.applyGroupAddConfiguration(this);
    }

    public void start() {
        super.start();
        String redisHost = DockerClientFactory.instance().dockerHostIpAddress();
        String redisPort = String.valueOf(container.getMappedPort(6379));
        System.setProperty("spring.data.redis.host", redisHost);
        System.setProperty("spring.data.redis.port", redisPort);
        System.setProperty("spring.data.redis.password", REDIS_PASSWORD);
        log.info("Started Redis container at {}:{} with password authentication", redisHost, redisPort);
    }

    private static RedisTestContainersExtension getInstance() {
        if (container == null) {
            container = new RedisTestContainersExtension();
        }
        return container;
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        getInstance().start();
    }
}
