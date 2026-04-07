package org.gupang.deliveryservice.infrastructure.client;

import org.gupang.deliveryservice.infrastructure.dto.UpdateUserStatusRequest;
import org.gupang.deliveryservice.infrastructure.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/api/v1/admin/delivery-managers")
    List<UserResponseDto> getDeliveryManager(@RequestParam UUID hubId);


    @PatchMapping("/api/v1//users/{userId}/delivery-settings")
    void  updateStaus(@PathVariable UUID userId, @RequestBody UpdateUserStatusRequest request);
}
