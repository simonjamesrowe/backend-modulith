package com.simonjamesrowe.model.cms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WebhookEventDTO(
    String event,
    @JsonProperty("created_at")
    ZonedDateTime createdAt,
    String model,
    JsonNode entry
) {
}