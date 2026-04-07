package org.gupang.deliveryservice.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CompanyResponseDto(
        @JsonProperty(value = "id")
        UUID companyId,
        UUID hubId,
        @JsonProperty(value = "name")
        String companyName
) {
}
