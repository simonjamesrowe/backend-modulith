package com.simonjamesrowe.component.test.kafka;

import com.simonjamesrowe.component.test.containers.ContainerConfigurationUtils;
import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

@Slf4j
public class SchemaRegistryContainerExtension extends GenericContainer {

    private static final String IMAGE_NAME = "confluentinc/cp-schema-registry:7.5.1";

    public SchemaRegistryContainerExtension() {
        super(IMAGE_NAME);
        ContainerConfigurationUtils.applyGroupAddConfiguration(this);
    }

    public SchemaRegistryContainerExtension withKafkaContainerBootstrapServers(
            final KafkaContainerExtension kafkaContainer,
                                                                               final int kafkaPort,
                                                                               final int schemaRegistryPort) {
        this.withExposedPorts(schemaRegistryPort)
            .withNetwork(kafkaContainer.getNetwork())
            .withEnv("SCHEMA_REGISTRY_KAFKASTORE_BOOTSTRAP_SERVERS", "PLAINTEXT://kafka:" + kafkaPort)
            .withEnv("SCHEMA_REGISTRY_LISTENERS", "http://0.0.0.0:" + schemaRegistryPort)
            .withEnv("SCHEMA_REGISTRY_HOST_NAME", "schemaregistry")
            .withEnv("SCHEMA_REGISTRY_KAFKASTORE_SECURITY_PROTOCOL", "PLAINTEXT")
            .waitingFor(Wait.forHttp("/subjects"));
        log.info("SchemaRegistryContainerExtension :: configuring container with properties: {}",
                getEnvMap());
        return this;
    }
}