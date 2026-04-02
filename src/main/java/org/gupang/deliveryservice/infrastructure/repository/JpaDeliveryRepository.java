package org.gupang.deliveryservice.infrastructure.repository;

import org.gupang.deliveryservice.domain.entity.Delivery;
import org.gupang.deliveryservice.domain.repository.DeliveryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID>, DeliveryRepository {

}
