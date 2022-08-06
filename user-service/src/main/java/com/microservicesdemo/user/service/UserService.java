package com.microservicesdemo.user.service;

import com.microservicesdemo.user.model.Role;
import com.microservicesdemo.user.model.User;
import com.microservicesdemo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    /**
     * Finds a user with username in the database
     *
     * @param username
     * @return
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Finds a user with email in the database
     *
     * @param email
     * @return
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Finds a user with id in the database
     *
     * @param id
     * @return
     */
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Save the user to the database
     *
     * @param user
     * @return
     */
    @Transactional
    public User save(User user) {
        log.info("Trying to save new user to db: [{}]", user.toString());
        userRepository.save(user);
        log.info("Created new user and saved to db: [{}]", user.toString());
        return user;
    }

    /**
     * Checks if the user exists with given email
     *
     * @param email
     * @return
     */
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Checks if the user eixts with given username
     *
     * @param username
     * @return
     */
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Sets to have what roles the new user while creating a new user
     *
     * @param isToBeMadeAdmin
     * @return
     */
    private Set<Role> getRolesForNewUser(Boolean isToBeMadeAdmin) {
        Set<Role> newUserRoles = new HashSet<>(roleService.findAll());
        if (!isToBeMadeAdmin)
            newUserRoles.removeIf(Role::isAdminRole);

        log.info("Setting user roles: {}", newUserRoles);
        return newUserRoles;
    }



}
