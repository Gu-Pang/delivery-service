package org.gupang.deliveryservice.application.event;

import lombok.Getter;

import java.util.UUID;
@Getter
public class OrderReadyEvent {
    private UUID orderId;
    private UUID supplierId;
    private UUID receiverId;
    private String address;
    private String addressDetail;
    private String recipientName;
}
