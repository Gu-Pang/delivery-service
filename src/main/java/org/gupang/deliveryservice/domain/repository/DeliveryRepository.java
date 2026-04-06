package org.gupang.deliveryservice.domain.repository;

import org.gupang.deliveryservice.domain.entity.Delivery;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository  {
    Delivery save(Delivery delivery);

    Optional<Delivery> findById(UUID uuid);
}
