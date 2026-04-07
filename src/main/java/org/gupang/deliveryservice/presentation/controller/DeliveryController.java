package org.gupang.deliveryservice.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.gupang.deliveryservice.application.dto.CompleteRouteCommand;
import org.gupang.deliveryservice.application.dto.CreateDeliveryCommand;
import org.gupang.deliveryservice.application.dto.StartDeliveryCommand;
import org.gupang.deliveryservice.application.service.DeliveryService;
import org.gupang.deliveryservice.presentation.dto.request.CreateDeliveryRequestDto;
import org.gupang.deliveryservice.presentation.dto.response.GetDeliveryResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/{deliveryId}")
    public ResponseEntity<GetDeliveryResponseDto> getDelivery(
            @PathVariable UUID deliveryId
    ) {
        return ResponseEntity.ok(deliveryService.getDelivery(deliveryId));
    }

    @PostMapping
    public ResponseEntity<Void> createDelivery(@RequestBody CreateDeliveryRequestDto dto){
        CreateDeliveryCommand command = new CreateDeliveryCommand(
                dto.orderId(),
                dto.supplierId(),
                dto.receiverId(),
                dto.address(),
                dto.addressDetail(),
                dto.recipientName()
        );

        deliveryService.createDelivery(command);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/start")
    public ResponseEntity<Void> startDelivery (@PathVariable UUID id){
        StartDeliveryCommand command = new StartDeliveryCommand(id);

        deliveryService.startDelivery(command);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{routeId}/routecomplete")
    public ResponseEntity<Void> completeRoute(@PathVariable UUID routeId){
        CompleteRouteCommand command = new CompleteRouteCommand(routeId);

        deliveryService.completeRoute(command);

        return ResponseEntity.ok().build();
    }
}