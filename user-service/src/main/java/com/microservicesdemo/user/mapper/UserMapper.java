package com.microservicesdemo.user.mapper;

import com.microservicesdemo.user.dto.UserRequest;
import com.microservicesdemo.user.dto.UserResponse;
import com.microservicesdemo.user.dto.UserUpdateRequest;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponse fromUser(UserRepresentation userRepresentation);

    UserRepresentation toUser(UserRequest userRequest);

    UserRepresentation toUserFromUserUpdateRequest(UserUpdateRequest userUpdateRequest);

    List<UserResponse> fromUsers(List<UserRepresentation> userRepresentationList);
}
