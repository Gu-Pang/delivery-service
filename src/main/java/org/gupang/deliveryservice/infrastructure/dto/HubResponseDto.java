package org.gupang.deliveryservice.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record HubResponseDto(
        @JsonProperty(value = "start_hub_id")
        UUID startHubId,
        @JsonProperty(value = "start_hub_name")
        String startHubName,
        @JsonProperty(value = "end_hub_id")
        UUID endHubId,
        @JsonProperty(value = "end_hub_name")
        String endHubName,
        @JsonProperty(value = "estimated_distance")
        int estimatedDistance,
        @JsonProperty(value = "estimated_duration")
        int estimatedDuration
) {
}
