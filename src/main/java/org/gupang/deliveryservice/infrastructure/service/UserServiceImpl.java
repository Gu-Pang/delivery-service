package org.gupang.deliveryservice.infrastructure.service;

import lombok.RequiredArgsConstructor;
import org.gupang.deliveryservice.application.model.UserInfo;
import org.gupang.deliveryservice.domain.service.UserService;
import org.gupang.deliveryservice.infrastructure.client.UserClient;
import org.gupang.deliveryservice.infrastructure.dto.UpdateUserStatusRequest;
import org.gupang.deliveryservice.infrastructure.dto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserClient userClient;

    @Override
    public List<UserInfo> getUser(UUID hubId){
        List<UserResponseDto> managers = userClient.getDeliveryManager(hubId);
        List<UserInfo> users = managers.stream().map(UserInfo::from).toList();
        return users.stream()
                .filter(userInfo -> "HUB".equals(userInfo.deliveryType()))
                .filter(u -> "AVAILABLE".equals(u.status()))
                .toList();
    }

    @Override
    public void updateStatus(UUID userId, String staus){
        userClient.updateStaus(userId, new UpdateUserStatusRequest(staus));
    }
}
