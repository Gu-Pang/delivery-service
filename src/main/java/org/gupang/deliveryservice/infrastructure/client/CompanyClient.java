package org.gupang.deliveryservice.infrastructure.client;

import org.gupang.deliveryservice.infrastructure.dto.CompanyResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "company-server")
public interface CompanyClient {

    @GetMapping("/api/v1/companies/{companyId}")
    CompanyResponseDto getCompany(@PathVariable UUID companyId);

}
