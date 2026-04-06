package org.gupang.deliveryservice.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.gupang.deliveryservice.application.model.HubInfo;
import org.gupang.deliveryservice.domain.service.HubService;
import org.gupang.deliveryservice.infrastructure.client.HubClient;
import org.gupang.deliveryservice.infrastructure.dto.HubResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HubServiceImpl implements HubService {

    private final HubClient hubClient;

    @Override
    public List<HubInfo> getHub(UUID startHubId, UUID endHubId){
        List<HubResponseDto> hubResponseDto = hubClient.getHub(startHubId, endHubId);

        return hubResponseDto.stream()
                .map(HubInfo::from)
                .toList();
    }
}
