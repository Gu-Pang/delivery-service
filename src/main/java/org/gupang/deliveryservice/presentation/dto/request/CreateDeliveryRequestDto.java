package org.gupang.deliveryservice.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateDeliveryRequestDto(
        @NotNull UUID orderId,
        @NotNull UUID supplierId,
        @NotNull UUID receiverId,
        @NotBlank String address,
        String addressDetail,
        @NotBlank String recipientName

) {
}
