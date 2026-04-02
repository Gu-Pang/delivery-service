package org.gupang.deliveryservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.gupang.common.entity.BaseEntity;
import org.gupang.common.exception.CustomException;
import org.gupang.common.exception.ErrorCode;

import java.util.UUID;

@Entity
@Table(name = "p_delivery_route_records")
@Getter
public class DeliveryRouteRecords extends BaseEntity {

    @Id
    private UUID routeRecordId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Column
    private UUID startHubId;

    @Column
    private UUID endHubId;

    @Column
    private Double estimatedDistance; //예상거리

    @Column
    private Integer estimatedDuration; //예상 소요 시간

    @Column
    private Double actualDistance; //실제 거리

    @Column
    private Integer actualDuration; //실제 소요 시간

    @Column
    @Enumerated(EnumType.STRING)
    private RouteStatus routeStatus;

    @Column
    private UUID deliveryManagerId;

    @Column(nullable = false)
    private Integer sequence;

    public static DeliveryRouteRecords create(
            UUID startHubId,
            UUID endHubId,
            Double distance,
            Integer duration,
            int sequence) {
            DeliveryRouteRecords routeRecords = new DeliveryRouteRecords();
            routeRecords.routeRecordId = UUID.randomUUID();
            routeRecords.startHubId = startHubId;
            routeRecords.endHubId = endHubId;
            routeRecords.estimatedDistance = distance;
            routeRecords.estimatedDuration = duration;
            routeRecords.sequence = sequence;
            routeRecords.routeStatus = RouteStatus.READY;
            return routeRecords;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public void start() {
        if(this.routeStatus != RouteStatus.READY){
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
            //todo ErrorCode추가
        }
        this.routeStatus = RouteStatus.IN_PROGRESS;
    }

    public void complete() {
        if(this.routeStatus != RouteStatus.IN_PROGRESS){
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
        this.routeStatus = RouteStatus.COMPLETED;
    }

}
