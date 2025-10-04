package com.simonjamesrowe.model.cms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record JobResponseDTO(
    String id,
    String title,
    String company,
    String companyUrl,
    String shortDescription,
    String longDescription,
    ImageResponseDTO companyImage,
    ZonedDateTime createdAt,
    ZonedDateTime updatedAt,
    LocalDate startDate,
    LocalDate endDate,
    boolean includeOnResume,
    boolean education,
    String location
) implements CMSObject {
}