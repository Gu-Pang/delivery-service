package org.gupang.deliveryservice.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.gupang.common.entity.BaseEntity;
import org.gupang.common.exception.CustomException;
import org.gupang.common.exception.ErrorCode;
import org.gupang.deliveryservice.application.model.UserInfo;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_delivery_route_records")
@SQLDelete(sql = "UPDATE p_delivery_route_records SET deleted_at = now() WHERE route_record_id = ?")
@SQLRestriction("deleted_at IS NULL")
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
    private String startHubName;

    @Column
    private UUID endHubId;

    @Column
    private String endHubName;

    @Column
    private int estimatedDistance; //예상거리

    @Column
    private int estimatedDuration; //예상 소요 시간

    @Column
    private int actualDistance; //실제 거리

    @Column
    private int actualDuration; //실제 소요 시간

    @Column
    @Enumerated(EnumType.STRING)
    private RouteStatus routeStatus;

    @Column
    private UUID deliveryManagerId;

    @Column(nullable = false)
    private Integer sequence;

    public static DeliveryRouteRecords create(
            UUID startHubId,
            String startHubName,
            UUID endHubId,
            String endHubName,
            int distance,
            int duration,
            int sequence) {
            DeliveryRouteRecords routeRecords = new DeliveryRouteRecords();
            routeRecords.routeRecordId = UUID.randomUUID();
            routeRecords.startHubId = startHubId;
            routeRecords.startHubName = startHubName;
            routeRecords.endHubId = endHubId;
            routeRecords.endHubName = endHubName;
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

    public UUID assignManager(List<UserInfo> managers){
        if(managers == null || managers.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }

        UserInfo selected = managers.stream()
                .sorted((a, b) -> Integer.compare(a.sequence(), b.sequence()))
                .findFirst()
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        this.deliveryManagerId = selected.userId();

        return selected.userId();
    }

}
