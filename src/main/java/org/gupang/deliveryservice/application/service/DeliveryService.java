package org.gupang.deliveryservice.application.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.gupang.common.exception.CustomException;
import org.gupang.common.exception.ErrorCode;
import org.gupang.deliveryservice.application.dto.CreateDeliveryCommand;
import org.gupang.deliveryservice.domain.entity.Delivery;
import org.gupang.deliveryservice.domain.repository.DeliveryRepository;
import org.gupang.deliveryservice.infrastructure.client.CompanyClient;
import org.gupang.deliveryservice.infrastructure.dto.CompanyResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final CompanyClient companyClient;


//    public void createDelivery(OrderReadyEvent event){
    //todo kafka붙일 때 event사용
    public void createDelivery(CreateDeliveryCommand command){
        try {
            CompanyResponseDto supplier = companyClient.getCompany(command.supplierId());
            UUID startHubId = supplier.hubId();

            CompanyResponseDto receiver = companyClient.getCompany(command.receiverId());
            UUID endHubId = receiver.hubId();

            Delivery delivery = Delivery.create(
                    command.orderId(),
                    startHubId,
                    endHubId,
                    command.address(),
                    command.addressDetail(),
                    command.recipientName(),
                    LocalDateTime.now().plusDays(1)
                    //todo 추후 계산로직 추가
            );
        }catch (FeignException.FeignClientException e){
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
            //todo ErrorCode COMPANY_NOT_FOUND추가해서 적용
        }
//        deliveryRepository.save(delivery);
    }
}
