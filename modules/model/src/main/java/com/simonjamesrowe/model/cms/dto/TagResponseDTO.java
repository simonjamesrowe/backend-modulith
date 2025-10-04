package com.simonjamesrowe.model.cms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TagResponseDTO(
    String id,
    String name,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt,
    @JsonProperty("__v")
    int version
) {
}