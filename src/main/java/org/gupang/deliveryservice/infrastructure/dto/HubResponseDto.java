package org.gupang.deliveryservice.infrastructure.dto;

import java.util.UUID;

public record HubResponseDto(
        UUID startHubId,
        String startHubName,
        UUID endHubId,
        String endHubName,
        double estimatedDistance,
        int estimatedDuration
) {
}
