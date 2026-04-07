package org.gupang.deliveryservice.application.model;

import org.gupang.deliveryservice.infrastructure.dto.UserResponseDto;

import java.util.UUID;

public record UserInfo(
        UUID userId,
        String deliveryType,
        int sequence,
        String status
) {
    public static UserInfo from(UserResponseDto responseDto){
        return new UserInfo(
                responseDto.userId(),
                responseDto.deliveryType(),
                responseDto.sequence(),
                responseDto.status()
        );
    }
}
