package org.gupang.deliveryservice.application.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.gupang.common.exception.CustomException;
import org.gupang.common.exception.ErrorCode;
import org.gupang.deliveryservice.application.dto.CompanyInfo;
import org.gupang.deliveryservice.application.dto.CreateDeliveryCommand;
import org.gupang.deliveryservice.application.dto.HubInfo;
import org.gupang.deliveryservice.domain.entity.Delivery;
import org.gupang.deliveryservice.domain.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final CompanyService companyService;
    private final HubService hubService;


//    public void createDelivery(OrderReadyEvent event){
    //todo kafka붙일 때 event사용
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
}
