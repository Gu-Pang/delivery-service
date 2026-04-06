package org.gupang.deliveryservice.domain.service;

import org.gupang.deliveryservice.application.model.UserInfo;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserInfo> getUser(UUID hubId);
}
