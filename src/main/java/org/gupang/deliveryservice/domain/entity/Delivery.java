package org.gupang.deliveryservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.gupang.common.entity.BaseEntity;
import org.gupang.common.exception.CustomException;
import org.gupang.common.exception.ErrorCode;
import org.gupang.deliveryservice.application.dto.HubInfo;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "p_delivery")
@Getter
@SQLDelete(sql = "UPDATE p_delivery SET deleted_at = now() WHERE delivery_id = ?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor
public class Delivery extends BaseEntity {

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
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
        this.status = DeliveryStatus.IN_TRANSIT;
    }

    public void complete() {
        if( this.status != DeliveryStatus.IN_TRANSIT){
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
        this.status = DeliveryStatus.DELIVERED;
    }

    public void cancel() {
        if (this.status == DeliveryStatus.DELIVERED) {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
        this.status = DeliveryStatus.CANCELLED;
    }

    public static Delivery create(
            UUID orderId,
            UUID startHubId,
            UUID endHubId,
            String address,
            String addressDetail,
            String recipientName,
            LocalDateTime deliveryDeadline
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
        delivery.deliveryDeadline = deliveryDeadline;
        return delivery;
    }

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryRouteRecords> routes = new ArrayList<>();

    public void createRoutes(List<HubInfo> hubRoutes){
        int sequence = 1;

        for (HubInfo hub : hubRoutes) {
            DeliveryRouteRecords route = DeliveryRouteRecords.create(
                    hub.startHubId(),
                    hub.startHubName(),
                    hub.endHubId(),
                    hub.endHubName(),
                    hub.estimatedDistance(),
                    hub.estimatedDuration(),
                    sequence++
            );
            addRoute(route);
            //todo 생성 책임 대부분 route로 이임
        }
    }

    //route확장을 위한 protected
    protected void addRoute(DeliveryRouteRecords route) {
        routes.add(route);
        route.setDelivery(this);
    }

    private DeliveryRouteRecords findNextRoute(DeliveryRouteRecords current){
        return routes.stream()
                .filter(r -> r.getSequence() == current.getSequence() + 1)
                .findFirst()//찾은 것중에 처음을 반환
                .orElse(null);
    }
    public void completeRoute(UUID routeId) {
        DeliveryRouteRecords current = routes.stream()
                .filter(r -> r.getRouteRecordId().equals(routeId))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        current.complete();

        DeliveryRouteRecords next = findNextRoute(current);

        if (next != null) {
            next.start();
        } else {
            this.complete(); // 마지막이면 배송 완료?
        }
    }
}
