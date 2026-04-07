package org.gupang.deliveryservice.application.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gupang.common.exception.CustomException;
import org.gupang.common.exception.ErrorCode;
import org.gupang.deliveryservice.application.dto.CompleteRouteCommand;
import org.gupang.deliveryservice.application.dto.CreateDeliveryCommand;
import org.gupang.deliveryservice.application.model.UserInfo;
import org.gupang.deliveryservice.domain.service.UserService;
import org.gupang.deliveryservice.presentation.dto.response.GetDeliveryResponseDto;
import org.gupang.deliveryservice.application.dto.StartDeliveryCommand;
import org.gupang.deliveryservice.application.model.CompanyInfo;
import org.gupang.deliveryservice.application.model.HubInfo;
import org.gupang.deliveryservice.domain.entity.Delivery;
import org.gupang.deliveryservice.domain.entity.DeliveryRouteRecords;
import org.gupang.deliveryservice.domain.entity.RouteStatus;
import org.gupang.deliveryservice.domain.repository.DeliveryRepository;
import org.gupang.deliveryservice.domain.service.CompanyService;
import org.gupang.deliveryservice.domain.service.HubService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final CompanyService companyService;
    private final HubService hubService;
    private final UserService userService;

//    public void createDelivery(OrderReadyEvent event){
    //todo kafka붙일 때 event사용
    @Transactional
    public void createDelivery(CreateDeliveryCommand command){
        try {
            CompanyInfo supplier = companyService.getCompany(command.supplierId());
            CompanyInfo receiver = companyService.getCompany(command.receiverId());

            Delivery delivery = command.toEntity(supplier, receiver);

            List<HubInfo> hubRoutes = hubService.getHub(supplier.hubId(), receiver.hubId());

            delivery.createRoutes(hubRoutes);

            deliveryRepository.save(delivery);
        }catch (FeignException.FeignClientException e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            //todo ErrorCode COMPANY_NOT_FOUND추가해서 적용
        }

    }

    @Transactional
    public void startDelivery(StartDeliveryCommand command) {

        Delivery delivery = deliveryRepository.findById(command.deliveryId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        DeliveryRouteRecords firstRoute = delivery.getCurrentRoute();

        try{
            List<UserInfo> managers = userService.getUser(firstRoute.getStartHubId());

            UUID managerId = firstRoute.assignManager(managers);

            userService.updateStatus(managerId, "UNAVAILABLE");

        }catch (FeignException e){
            // 배정 실패 → 그냥 진행
            log.warn("배송 담당자 배정 실패", e);
        }

        delivery.start();
    }

    @Transactional
    public void completeRoute(CompleteRouteCommand command){

        Delivery delivery = deliveryRepository.findByRoutes_RouteRecordId(command.routeId())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        delivery.completeRoute(command.routeId());

        DeliveryRouteRecords nextRoute = delivery.getCurrentRoute();
        if(nextRoute != null){
            try {
                List<UserInfo> managers = userService.getUser(nextRoute.getStartHubId());

                UUID managerId = nextRoute.assignManager(managers);

                userService.updateStatus(managerId, "UNAVAILABLE");
            }catch (FeignException e){
                // 배정 실패 → 그냥 진행
                log.warn("배송 담당자 배정 실패", e);
            }
        }
    }

    public GetDeliveryResponseDto getDelivery(UUID deliveryId){
        Delivery delivery = deliveryRepository.findWithRoutes(deliveryId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));

        DeliveryRouteRecords current = delivery.getCurrentRoute();

        return GetDeliveryResponseDto.from(delivery, current);
    }


}
