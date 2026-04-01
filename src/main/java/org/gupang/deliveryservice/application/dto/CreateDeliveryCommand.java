package org.gupang.deliveryservice.application.dto;

import java.util.UUID;

public record CreateDeliveryCommand(
        UUID orderId,
        UUID supplierId,
        UUID receiverId,
        String address,
        String addressDetail,
        String recipientName
) {
}
