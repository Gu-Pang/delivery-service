package org.gupang.deliveryservice.presentation.dto.response;

import org.gupang.deliveryservice.domain.entity.Delivery;
import org.gupang.deliveryservice.domain.entity.DeliveryRouteRecords;

import java.util.List;
import java.util.UUID;

public record GetDeliveryResponseDto(
        UUID deliveryId,
        UUID orderId,
        String status,

        RouteDto currentRoute,

        List<RouteDto> routes
) {
    public static GetDeliveryResponseDto from(Delivery delivery, DeliveryRouteRecords current) {
        return new GetDeliveryResponseDto(delivery.getDeliveryId(),
                delivery.getOrderId(),
                delivery.getStatus().name(),

                new RouteDto(
                        current.getRouteRecordId(),
                        current.getStartHubName(),
                        current.getEndHubName(),
                        current.getRouteStatus().name(),
                        current.getSequence()),

                delivery.getRoutes().stream().map(
                        r -> new RouteDto(
                                r.getRouteRecordId(),
                                r.getStartHubName(),
                                r.getEndHubName(),
                                r.getRouteStatus().name(),
                                r.getSequence()
                        )
                ).toList());
    }
}
