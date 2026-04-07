package org.gupang.deliveryservice.infrastructure.repository;

import org.gupang.deliveryservice.domain.entity.Delivery;
import org.gupang.deliveryservice.domain.repository.DeliveryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID>, DeliveryRepository {

    @Override
    @Query("select d from p_delivery d join fetch d.routes where d.deliveryId = :deliveryId")
    Optional<Delivery> findWithRoutes(UUID deliveryId);
}
