package org.gupang.deliveryservice.application.service;

import org.gupang.deliveryservice.application.model.CompanyInfo;

import java.util.UUID;

public interface CompanyService {

    CompanyInfo getCompany(UUID companyId);
}
