package org.gupang.deliveryservice.presentation.dto.response;

import java.util.UUID;

public record RouteDto(
        UUID routeId,
        String startHubName,
        String endHubName,
        String status,
        Integer sequence
) {
}
