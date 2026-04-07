package org.gupang.deliveryservice.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.gupang.deliveryservice.application.model.CompanyInfo;
import org.gupang.deliveryservice.domain.service.CompanyService;
import org.gupang.deliveryservice.infrastructure.client.CompanyClient;
import org.gupang.deliveryservice.infrastructure.dto.CompanyResponseDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyClient companyClient;

    @Override
    public CompanyInfo getCompany(UUID companyId){
        CompanyResponseDto companyResponseDto = companyClient.getCompany(companyId);
        return CompanyInfo.from(companyResponseDto);
    }
}
