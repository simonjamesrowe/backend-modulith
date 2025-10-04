package com.simonjamesrowe.model.cms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SocialMediaResponseDTO(
    String id,
    String type,
    String link,
    String name,
    boolean includeOnResume
) {
}