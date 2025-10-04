package com.simonjamesrowe.component.test.elasticsearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
@Profile("elasticsearch")
public class ElasticsearchTestConfiguration extends ElasticsearchConfiguration {

    @Value("${elasticsearch.host:localhost}:${elasticsearch.port:9200}")
    private String elasticsearchHostPort;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchHostPort)
                .build();
    }
}