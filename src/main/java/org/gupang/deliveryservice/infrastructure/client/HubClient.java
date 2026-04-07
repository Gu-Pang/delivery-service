package org.gupang.deliveryservice.infrastructure.client;

import org.gupang.deliveryservice.infrastructure.dto.HubResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "hub-server")
public interface HubClient {

    @GetMapping("/api/v1/hubs/routes")
    List<HubResponseDto> getHub(@RequestParam UUID startHubId, @RequestParam UUID endHubId);

}
