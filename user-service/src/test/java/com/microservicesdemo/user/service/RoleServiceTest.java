package com.microservicesdemo.user.service;

import com.microservicesdemo.user.model.Role;
import com.microservicesdemo.user.model.RoleName;
import com.microservicesdemo.user.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @InjectMocks
    private RoleService roleService;

    @Mock
    private RoleRepository roleRepository;


    @Test
    public void shouldFindAll() {
        // given
        List<Role> roles = new ArrayList<>(List.of(
                Role.builder().id(UUID.randomUUID().toString()).role(RoleName.ROLE_ADMIN).build(),
                Role.builder().id(UUID.randomUUID().toString()).role(RoleName.ROLE_USER).build()
        ));

        given(roleRepository.findAll()).willReturn(roles);

        // when
        List<Role> expectedRoles = roleService.findAll();

        // then
        verify(roleRepository, times(1)).findAll();
        assertEquals(2, expectedRoles.size());
    }
}