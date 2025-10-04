package com.simonjamesrowe.model.cms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.ZonedDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BlogResponseDTO(
    String id,
    boolean published,
    List<TagResponseDTO> tags,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt,
    List<SkillResponseDTO> skills,
    String shortDescription,
    String title,
    String content,
    ImageResponseDTO image
) implements CMSObject {
}