package org.gupang.deliveryservice.application.dto;

import java.util.UUID;

public record StartDeliveryCommand(
        UUID deliveryId
) {
}
