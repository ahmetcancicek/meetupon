package com.microservicesdemo.auth.mapper;

import com.microservicesdemo.auth.dto.RegistrationRequest;
import com.microservicesdemo.auth.dto.UpdateUserRequest;
import com.microservicesdemo.auth.dto.RegistrationResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserRepresentationMapper {

    UserRepresentationMapper INSTANCE = Mappers.getMapper(UserRepresentationMapper.class);

    RegistrationResponse fromUserRepresentation(UserRepresentation userRepresentation);

    UserRepresentation toUserRepresentation(RegistrationRequest userRequest);

    UserRepresentation toUserRepresentationFromUserUpdateRequest(UpdateUserRequest updateUserRequest);

    List<RegistrationResponse> fromUserRepresentationList(List<UserRepresentation> userRepresentationList);
}
