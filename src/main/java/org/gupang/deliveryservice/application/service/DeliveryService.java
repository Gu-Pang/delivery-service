package org.gupang.deliveryservice.application.service;

import lombok.RequiredArgsConstructor;
import org.gupang.deliveryservice.application.event.OrderReadyEvent;
import org.gupang.deliveryservice.domain.entity.Delivery;
import org.gupang.deliveryservice.domain.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;


    public void createDelivery(OrderReadyEvent event){
        UUID startHubId = UUID.randomUUID();
        //todo 추후 Hub죄회 임시로 UUID 생성
        UUID endHubId = UUID.randomUUID();
        //todo 추후 Hub죄회 임시로 UUID 생성

        Delivery delivery = Delivery.create(
                event.getOrderId(),
                startHubId,
                endHubId,
                event.getAddress(),
                event.getAddressDetail(),
                event.getRecipientName(),
                LocalDateTime.now().plusDays(1)
                //todo 추후 계산로직 추가
        );

        deliveryRepository.save(delivery);
    }
}
