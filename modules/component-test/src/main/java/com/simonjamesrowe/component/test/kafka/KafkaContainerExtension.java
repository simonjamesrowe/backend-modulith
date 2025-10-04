package com.simonjamesrowe.component.test.kafka;

import com.simonjamesrowe.component.test.containers.ContainerConfigurationUtils;
import com.simonjamesrowe.component.test.utils.PortUtils;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.Network;
import org.testcontainers.kafka.ConfluentKafkaContainer;

@Slf4j
public final class KafkaContainerExtension extends ConfluentKafkaContainer implements BeforeAllCallback {

    private static final String KAFKA_IMAGE_NAME = "confluentinc/cp-kafka:7.5.1";
    private static int kafkaPort = PortUtils.randomPort();
    private static int schemaRegistryPort = PortUtils.randomPort();
    private static final Network NETWORK = Network.newNetwork();
    private static KafkaContainerExtension container;

    public KafkaContainerExtension() {
        super(KAFKA_IMAGE_NAME);
        withListener("kafka:" + kafkaPort).withNetwork(NETWORK);
        ContainerConfigurationUtils.applyGroupAddConfiguration(this);
    }

    public void start(final WithKafkaContainer withKafkaContainer) {
        container.start();
        if (withKafkaContainer.schemaRegistry()) {
            startKafkaSchemaRegistry(withKafkaContainer);
        }
        System.setProperty(withKafkaContainer.bootstrapServerProperty(), container.getBootstrapServers());
        log.info("Started Kafka container with properties {}", container.getEnv());
    }

    private void startKafkaSchemaRegistry(final WithKafkaContainer withKafkaContainer) {
        final SchemaRegistryContainerExtension schemaRegistryContainer = new SchemaRegistryContainerExtension()
            .withKafkaContainerBootstrapServers(container, kafkaPort, schemaRegistryPort);
        schemaRegistryContainer.start();
        log.info("Started Schema Registry with properties {}={}",
                 withKafkaContainer.schemaRegistryProperty(),
                 schemaRegistryContainer.getMappedPort(schemaRegistryPort));
        System.setProperty(withKafkaContainer.schemaRegistryProperty(),
                           "http://" + schemaRegistryContainer.getHost()
                               + ":" + schemaRegistryContainer.getMappedPort(schemaRegistryPort));

    }

    private static void startInstance(final WithKafkaContainer withKafkaContainer) {
        if (container == null) {
            container = new KafkaContainerExtension();
            container.start(withKafkaContainer);
        }
    }

    @Override
    public void beforeAll(final ExtensionContext context) throws Exception {
        Arrays.stream(context.getTestClass().get().getAnnotations())
            .filter(a -> a instanceof WithKafkaContainer)
            .map(a -> (WithKafkaContainer) a)
            .findFirst()
            .ifPresent(e -> startInstance(e));
    }
}