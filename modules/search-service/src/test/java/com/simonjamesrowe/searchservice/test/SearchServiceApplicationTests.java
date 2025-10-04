package com.simonjamesrowe.searchservice.test;

import com.simonjamesrowe.component.test.BaseComponentTest;
import com.simonjamesrowe.component.test.ComponentTest;
import com.simonjamesrowe.component.test.elasticsearch.WithElasticsearchContainer;
import com.simonjamesrowe.component.test.kafka.WithKafkaContainer;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;

@WithElasticsearchContainer
@WithKafkaContainer
@ComponentTest
@AutoConfigureWireMock
public class SearchServiceApplicationTests extends BaseComponentTest {


    @Test
    void contextLoads() {
        // Test that the Spring context loads successfully
    }
}