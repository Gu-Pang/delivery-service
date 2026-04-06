package org.gupang.deliveryservice.domain.repository;

import org.gupang.deliveryservice.domain.entity.Delivery;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository  {
    Delivery save(Delivery delivery);

    Optional<Delivery> findById(UUID uuid);

    Optional<Delivery> findByRoutes_RouteRecordId(UUID routeId);

    Optional<Delivery> findWithRoutes(UUID deliveryId);
}
