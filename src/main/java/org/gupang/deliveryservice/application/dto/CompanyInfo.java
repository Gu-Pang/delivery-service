package org.gupang.deliveryservice.application.dto;

import org.gupang.deliveryservice.infrastructure.dto.CompanyResponseDto;

import java.util.UUID;

public record CompanyInfo(
        UUID companyId,
        UUID hubId,
        String companyName
) {

    public static CompanyInfo from(CompanyResponseDto responseDto){
        return new CompanyInfo(
                responseDto.companyId(),
                responseDto.hubId(),
                responseDto.companyName()
        );
    }


}
