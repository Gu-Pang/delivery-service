package org.gupang.deliveryservice.infrastructure.dto;

import java.util.UUID;

public record UserResponseDto(
        UUID userId,
        String deliveryType,
        int sequence,
        String status
) {
}
