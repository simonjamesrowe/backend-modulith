package com.simonjamesrowe.model.cms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProfileResponseDTO(
    String id,
    String name,
    String title,
    String headline,
    String description,
    String location,
    String phoneNumber,
    String primaryEmail,
    String secondaryEmail
) {
}