package com.simonjamesrowe.model.cms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SkillsGroupResponseDTO(
    String id,
    String name,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt,
    String description,
    double rating,
    Integer order,
    ImageResponseDTO image,
    List<SkillResponseDTO> skills
) implements CMSObject {
}