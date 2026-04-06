package org.gupang.deliveryservice.application.dto;

import java.util.UUID;

public record CompleteRouteCommand(
        UUID routeId
) {

}
