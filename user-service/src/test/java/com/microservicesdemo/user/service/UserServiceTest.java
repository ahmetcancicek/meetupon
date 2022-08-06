package com.microservicesdemo.user.service;

import com.microservicesdemo.user.model.Role;
import com.microservicesdemo.user.model.RoleName;
import com.microservicesdemo.user.model.User;
import com.microservicesdemo.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void givenUser_whenSaveUser_thenReturnUser() {
        // given
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("georgechair")
                .password("G9dl9B6nkm")
                .email("georgechair@email.com")
                .active(true)
                .roles(Set.of(Role.builder()
                        .id(UUID.randomUUID().toString())
                        .role(RoleName.ROLE_USER)
                        .build()))
                .build();

        given(userRepository.save(any())).willReturn(user);

        // when
        User savedUser = userService.save(user);

        // then
        verify(userRepository, times(1)).save(any());
        assertNotNull(savedUser, "Object must not be null");
        assertEquals(user, savedUser, "Objects should be equal");
    }

    @Test
    public void givenUserAsAdmin_whenSaveUser_thenReturnUserAsAdmin() {
        // given
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("georgechair")
                .password("G9dl9B6nkm")
                .email("georgechair@email.com")
                .active(true)
                .roles(Set.of(Role.builder()
                        .id(UUID.randomUUID().toString())
                        .role(RoleName.ROLE_ADMIN)
                        .build()))
                .build();

        given(userRepository.save(any())).willReturn(user);

        // when
        User savedUser = userService.save(user);

        // then
        verify(userRepository, times(1)).save(any());
        assertNotNull(savedUser, "Object must not be null");
        assertEquals(user, savedUser, "Objects should be equal");
    }

    @Test
    public void givenUserAsCustomer_whenCreateUser_thenReturnUserAsCustomer() {
        // TODO: Fix shouldCreateUser()
    }

    @Test
    public void givenUserAsAdmin_whenCreateUser_thenReturnUserAsAdmin() {
        // TODO: Fix shouldCreateUserAsAdmin()
    }

    @Test
    public void givenExistingUsername_whenFindUser_thenReturnUser() {
        // given
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("georgechair")
                .password("G9dl9B6nkm")
                .email("georgechair@email.com")
                .active(true)
                .roles(Set.of(Role.builder()
                        .id(UUID.randomUUID().toString())
                        .role(RoleName.ROLE_USER)
                        .build()))
                .build();

        given(userRepository.findByUsername(any())).willReturn(Optional.of(user));

        // when
        Optional<User> expectedUser = userService.findByUsername(user.getUsername());

        // then
        verify(userRepository, times(1)).findByUsername(any());
        assertTrue(expectedUser.isPresent(), "Returned must not be null");
        assertEquals(user.getEmail(), expectedUser.get().getEmail(), "Email must be equal");
        assertEquals(1, expectedUser.get().getRoles().size(), "Roles size must be equal");
        assertEquals(user.getActive(), expectedUser.get().getActive(), "Active must be equal");
        assertEquals(user.getPassword(), expectedUser.get().getPassword(), "Password must be equal");
    }

    @Test
    public void givenExistingEmail_whenFindUser_thenReturnUser() {
        // given
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("georgechair")
                .password("G9dl9B6nkm")
                .email("georgechair@email.com")
                .active(true)
                .roles(Set.of(Role.builder()
                        .id(UUID.randomUUID().toString())
                        .role(RoleName.ROLE_USER)
                        .build()))
                .build();

        given(userRepository.findByEmail(any())).willReturn(Optional.of(user));

        // when
        Optional<User> expectedUser = userService.findByEmail(user.getEmail());

        // then
        verify(userRepository, times(1)).findByEmail(any());
        assertTrue(expectedUser.isPresent(), "Returned must not be null");
        assertEquals(user.getEmail(), expectedUser.get().getEmail(), "Email must be equal");
        assertEquals(1, expectedUser.get().getRoles().size(), "Roles size must be equal");
        assertEquals(user.getActive(), expectedUser.get().getActive(), "Active must be equal");
        assertEquals(user.getPassword(), expectedUser.get().getPassword(), "Password must be equal");
    }

    @Test
    public void givenExistingId_whenFindUser_thenReturnUser() {
        // given
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("georgechair")
                .password("G9dl9B6nkm")
                .email("georgechair@email.com")
                .active(true)
                .roles(Set.of(Role.builder()
                        .id(UUID.randomUUID().toString())
                        .role(RoleName.ROLE_USER)
                        .build()))
                .build();

        given(userRepository.findById(any())).willReturn(Optional.of(user));

        // when
        Optional<User> expectedUser = userService.findById(user.getId());

        // then
        verify(userRepository, times(1)).findById(any());
        assertTrue(expectedUser.isPresent(), "Returned must not be null");
        assertEquals(user.getEmail(), expectedUser.get().getEmail(), "Email must be equal");
        assertEquals(1, expectedUser.get().getRoles().size(), "Roles size must be equal");
        assertEquals(user.getActive(), expectedUser.get().getActive(), "Active must be equal");
        assertEquals(user.getPassword(), expectedUser.get().getPassword(), "Password must be equal");
    }

    @Test
    public void givenExistingEmail_whenFindUser_thenReturnTrue() {
        // given
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("georgechair")
                .password("G9dl9B6nkm")
                .email("georgechair@email.com")
                .active(true)
                .roles(Set.of(Role.builder()
                        .id(UUID.randomUUID().toString())
                        .role(RoleName.ROLE_USER)
                        .build()))
                .build();

        given(userRepository.existsByEmail(any())).willReturn(true);

        // then
        assertTrue(userService.existsByEmail(user.getEmail()));
    }

    @Test
    public void givenNotExistingEmail_whenFindUser_thenReturnFalse() {
        // given
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("georgechair")
                .password("G9dl9B6nkm")
                .email("georgechair@email.com")
                .active(true)
                .roles(Set.of(Role.builder()
                        .id(UUID.randomUUID().toString())
                        .role(RoleName.ROLE_USER)
                        .build()))
                .build();

        // then
        assertFalse(userService.existsByEmail(user.getEmail()));
    }

    @Test
    public void givenExistingUsername_whenFindUser_thenReturnTrue() {
        // given
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("georgechair")
                .password("G9dl9B6nkm")
                .email("georgechair@email.com")
                .active(true)
                .roles(Set.of(Role.builder()
                        .id(UUID.randomUUID().toString())
                        .role(RoleName.ROLE_USER)
                        .build()))
                .build();

        given(userRepository.existsByUsername(any())).willReturn(true);

        // then
        assertTrue(userService.existsByUsername(user.getUsername()));
    }

    @Test
    public void givenNotExistingUsername_whenFindUser_thenReturnFalse() {
        // given
        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username("georgechair")
                .password("G9dl9B6nkm")
                .email("georgechair@email.com")
                .active(true)
                .roles(Set.of(Role.builder()
                        .id(UUID.randomUUID().toString())
                        .role(RoleName.ROLE_USER)
                        .build()))
                .build();

        // then
        assertFalse(userService.existsByUsername(user.getUsername()));
    }
}