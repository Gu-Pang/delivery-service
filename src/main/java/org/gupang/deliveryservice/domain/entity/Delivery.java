package org.gupang.deliveryservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "p_delivery")
@Getter
@NoArgsConstructor
public class Delivery {

    @Id
    @Column(name="delivery_id")//yaml로 스네이크로 변경가능하다. 라고 한 거 같은데....일단 이렇게 설정
    private UUID deliveryId;

    @Column(name = "order_id", nullable = false)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Column(name = "start_hub_id", nullable = false)
    private UUID startHubId;

    @Column(name = "end_hub_id", nullable = false)
    private UUID endHubId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "recipient_name", nullable = false)
    private String recipientName;

    @Column(name = "delivery_manager_id")
    private UUID deliveryManagerId;

    @Column(name = "delivery_deadline")//발송시한
    private LocalDateTime deliveryDeadline;


    public void start() {
        if (this.status != DeliveryStatus.READY) {
            throw new IllegalStateException();
            //todo ErrorCode적용
        }
        this.status = DeliveryStatus.IN_TRANSIT;
    }

    public void complete() {
        if( this.status != DeliveryStatus.IN_TRANSIT){
            throw new IllegalStateException();
            //todo ErrorCode적용
        }
        this.status = DeliveryStatus.DELIVERED;
    }

    public void cancel() {
        this.status = DeliveryStatus.CANCELLED;
    }

    public static Delivery create(
            UUID orderId,
            UUID startHubId,
            UUID endHubId,
            String address,
            String addressDetail,
            String recipientName
    ) {
        Delivery delivery = new Delivery();
        delivery.deliveryId = UUID.randomUUID();
        delivery.orderId = orderId;
        delivery.startHubId = startHubId;
        delivery.endHubId = endHubId;
        delivery.address = address;
        delivery.addressDetail = addressDetail;
        delivery.recipientName = recipientName;
        delivery.status = DeliveryStatus.READY;
        return delivery;
    }
}
