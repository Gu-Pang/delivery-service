package org.gupang.deliveryservice.infrastructure.client;

import org.gupang.deliveryservice.infrastructure.dto.HubResponseDto;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Primary
@Profile("dev")
public class MockHubClient implements HubClient{
    @Override
    public List<HubResponseDto> getHub(UUID StartHubId, UUID endHubId){
        int x = 100;
        int y = 200;
        String name1= "name1";
        String name2 = "name2";
        return List.of(new HubResponseDto(
                UUID.randomUUID(),
                name1,
                UUID.randomUUID(),
                name2,
                y,
                x),
                new HubResponseDto(
                        UUID.randomUUID(),
                        "name3",
                        UUID.randomUUID(),
                        "name4",
                        y,
                        x)
                );
    }
}
