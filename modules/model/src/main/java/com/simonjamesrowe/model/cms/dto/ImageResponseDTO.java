package com.simonjamesrowe.model.cms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ImageResponseDTO(
    String url,
    String name,
    int size,
    int width,
    int height,
    String mime,
    ImageFormats formats
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record ImageFormats(
        ImageResponseDTO thumbnail,
        ImageResponseDTO large,
        ImageResponseDTO medium,
        ImageResponseDTO small
    ) {
    }
}