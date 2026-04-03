package org.gupang.deliveryservice.infrastructure.dto;

import java.util.UUID;

public record CompanyResponseDto(
        UUID companyId,
        UUID hubId,
        String companyName
) {
}
