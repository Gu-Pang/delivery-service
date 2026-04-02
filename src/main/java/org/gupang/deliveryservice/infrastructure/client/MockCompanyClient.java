package org.gupang.deliveryservice.infrastructure.client;

import org.gupang.deliveryservice.infrastructure.dto.CompanyResponseDto;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
@Primary
@Profile("dev")
public class MockCompanyClient implements CompanyClient{

    @Override
    public CompanyResponseDto getCompany(UUID companyId){
        return new CompanyResponseDto(
                companyId,
                UUID.randomUUID(),
                "Mock Company"
        );
    }
}
