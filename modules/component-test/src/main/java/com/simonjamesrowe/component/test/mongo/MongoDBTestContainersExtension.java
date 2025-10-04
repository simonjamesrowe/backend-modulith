package com.simonjamesrowe.component.test.mongo;

import com.simonjamesrowe.component.test.containers.ContainerConfigurationUtils;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Runs test containers for MongoDB
 */
@Slf4j
public class MongoDBTestContainersExtension extends GenericContainer implements BeforeAllCallback {

    private static final String MONGO_USER = "user";
    private static final String MONGO_PASSWORD = UUID.randomUUID().toString();
    private static MongoDBTestContainersExtension container;

    public MongoDBTestContainersExtension() {
        super("mongo:4.4");
        withExposedPorts(27017);
        withEnv("MONGO_INITDB_ROOT_USERNAME", MONGO_USER);
        withEnv("MONGO_INITDB_ROOT_PASSWORD", MONGO_PASSWORD);
        waitingFor(
                Wait.forLogMessage(".*Waiting for connections.*", 1)
        );
        ContainerConfigurationUtils.applyGroupAddConfiguration(this);
    }

    public void start() {
        super.start();
        String mongoUri = String.format("mongodb://%s:%s@%s:%s/", MONGO_USER, MONGO_PASSWORD, DockerClientFactory
                .instance().dockerHostIpAddress(), container.getMappedPort(27017));
        System.setProperty("spring.data.mongodb.uri", mongoUri);
        System.setProperty("spring.data.mongodb.authentication-database", "admin");
        System.setProperty("spring.data.mongodb.database", "test");
        log.info("Started MongoDB container with URI: {}", mongoUri);
    }

    private static MongoDBTestContainersExtension getInstance() {
        if (container == null) {
            container = new MongoDBTestContainersExtension();
        }
        return container;
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        getInstance().start();
    }
}
