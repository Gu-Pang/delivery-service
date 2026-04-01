package org.gupang.deliveryservice.domain.repository;

import org.gupang.deliveryservice.domain.entity.Delivery;

public interface DeliveryRepository  {
    Delivery save(Delivery delivery);
}
