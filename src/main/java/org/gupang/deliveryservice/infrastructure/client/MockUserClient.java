package org.gupang.deliveryservice.infrastructure.client;

import org.gupang.deliveryservice.infrastructure.dto.UpdateUserStatusRequest;
import org.gupang.deliveryservice.infrastructure.dto.UserResponseDto;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Primary
@Profile("dev")
public class MockUserClient implements UserClient{

    @Override
    public List<UserResponseDto> getDeliveryManager(UUID userId, String deliveryType){
        return List.of(
                new UserResponseDto(
                        UUID.randomUUID(),
                        "HUB",
                        1,
                        "AVAILABLE"),
                new UserResponseDto(
                        UUID.randomUUID(),
                        "HUB",
                        2,
                        "AVAILABLE"));
    }

    @Override
    public void updateStaus(UUID userId, UpdateUserStatusRequest request) {

    }
}
