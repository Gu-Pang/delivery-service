package org.gupang.deliveryservice.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.gupang.deliveryservice.application.dto.CreateDeliveryCommand;
import org.gupang.deliveryservice.application.service.DeliveryService;
import org.gupang.deliveryservice.presentation.dto.request.CreateDeliveryRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping
    public String test() {
        return "ok";
    }

    @PostMapping("/test")
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
}
