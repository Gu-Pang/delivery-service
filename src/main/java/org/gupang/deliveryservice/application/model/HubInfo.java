package org.gupang.deliveryservice.application.model;

import org.gupang.deliveryservice.infrastructure.dto.HubResponseDto;

import java.util.UUID;

public record HubInfo(
        UUID startHubId,
        String startHubName,
        UUID endHubId,
        String endHubName,
        int estimatedDistance,
        int estimatedDuration
) {
    public static HubInfo from(HubResponseDto hubResponseDto){
        return new HubInfo(
                hubResponseDto.startHubId(),
                hubResponseDto.startHubName(),
                hubResponseDto.endHubId(),
                hubResponseDto.endHubName(),
                hubResponseDto.estimatedDistance(),
                hubResponseDto.estimatedDuration()
        );
    }
}
