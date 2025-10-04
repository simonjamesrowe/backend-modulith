package com.simonjamesrowe.model.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simonjamesrowe.model.cms.dto.WebhookEventDTO;
import com.simonjamesrowe.model.config.JacksonConfig;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class WebhookEventDeserializer extends JsonDeserializer<WebhookEventDTO> {
    
    public WebhookEventDeserializer() {
        super(createObjectMapper());
    }
    
    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(JacksonConfig.dateTimeModule());
        return mapper;
    }
}