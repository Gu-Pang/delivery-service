package org.gupang.deliveryservice.application.service;

import org.gupang.deliveryservice.application.dto.CompanyInfo;

import java.util.UUID;

public interface CompanyService {

    CompanyInfo getCompany(UUID companyId);
}
