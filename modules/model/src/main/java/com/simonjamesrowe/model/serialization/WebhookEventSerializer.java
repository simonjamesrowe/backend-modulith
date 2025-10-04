package com.simonjamesrowe.model.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.simonjamesrowe.model.cms.dto.WebhookEventDTO;
import com.simonjamesrowe.model.config.JacksonConfig;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class WebhookEventSerializer extends JsonSerializer<WebhookEventDTO> {
    
    public WebhookEventSerializer() {
        super(createObjectMapper());
    }
    
    private static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(JacksonConfig.dateTimeModule());
        return mapper;
    }
}