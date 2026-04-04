package org.gupang.deliveryservice.application.dto;

import org.gupang.deliveryservice.domain.entity.Delivery;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateDeliveryCommand(
        UUID orderId,
        UUID supplierId,
        UUID receiverId,
        String address,
        String addressDetail,
        String recipientName
) {

    public Delivery toEntity(
            CompanyInfo supplier,
            CompanyInfo receiver
    ){
        return Delivery.create(
                orderId,
                supplier.hubId(),
                receiver.hubId(),
                address,
                addressDetail,
                recipientName,
                LocalDateTime.now().plusDays(1)
                //todo 추후 계산로직 추가
        );
    }
}
