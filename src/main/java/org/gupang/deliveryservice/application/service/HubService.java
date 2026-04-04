package org.gupang.deliveryservice.application.service;

import org.gupang.deliveryservice.application.dto.HubInfo;

import java.util.List;
import java.util.UUID;

public interface HubService {

    List<HubInfo> getHub(UUID startHubId, UUID endHubId);
}
