package com.simonjamesrowe.model.cms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SkillResponseDTO(
    String id,
    String name,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt,
    String description,
    double rating,
    Integer order,
    ImageResponseDTO image,
    boolean includeOnResume
) implements CMSObject {
}