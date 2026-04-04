package org.gupang.deliveryservice.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.gupang.deliveryservice.application.dto.CompanyInfo;
import org.gupang.deliveryservice.application.service.CompanyService;
import org.gupang.deliveryservice.infrastructure.client.CompanyClient;
import org.gupang.deliveryservice.infrastructure.dto.CompanyResponseDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyClient companyClient;

    @Override
    public CompanyInfo getCompany(UUID companyId){
        CompanyResponseDto companyResponseDto = companyClient.getCompany(companyId);
        return CompanyInfo.from(companyResponseDto);
    }
}
