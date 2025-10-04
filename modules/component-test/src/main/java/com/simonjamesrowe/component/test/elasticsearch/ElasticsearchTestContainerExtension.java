package com.simonjamesrowe.component.test.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

/**
 * Runs test containers for Elasticsearch
 */
@Slf4j
@Order(0)
public class ElasticsearchTestContainerExtension extends ElasticsearchContainer implements BeforeAllCallback {
    public static final String CONTAINER_NAME = "docker.elastic.co/elasticsearch/elasticsearch:8.11.1";
    private static ElasticsearchTestContainerExtension container;

    public ElasticsearchTestContainerExtension() {
        super(CONTAINER_NAME);
        // Disable security for testing (Elasticsearch 8 has security enabled by default)
        withEnv("xpack.security.enabled", "false");
        // Ensure Elasticsearch binds to all network interfaces
        withEnv("network.host", "0.0.0.0");
        // Disable SSL for testing
        withEnv("xpack.security.http.ssl.enabled", "false");
        withEnv("xpack.security.transport.ssl.enabled", "false");
    }

    private ElasticsearchContainer getInstance() {
        if (container == null) {
            container = new ElasticsearchTestContainerExtension();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        String elasticSearchUrl = String.format(
                "http://%s:%s",
                DockerClientFactory.instance().dockerHostIpAddress(),
                container.getMappedPort(9200)
        );


        // Set system property for custom configuration
        System.setProperty("spring.elasticsearch.uris", elasticSearchUrl);
        System.setProperty("elasticsearch.host", DockerClientFactory.instance().dockerHostIpAddress());
        System.setProperty("elasticsearch.port", "" + container.getMappedPort(9200));
        log.info("Elastic Search test container started on port {}", container.getMappedPort(9200));
    }


    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        getInstance().start();
    }


}
