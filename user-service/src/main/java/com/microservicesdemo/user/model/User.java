package com.microservicesdemo.user.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Email
    @NotBlank
    @NaturalId
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;

    @NotNull
    @NotBlank
    @Length(min = 5)
    @Column(name = "username", unique = true, nullable = true, length = 20)
    private String username;

    @NotEmpty
    @Column(name = "password", nullable = false)
    private String password;

    @Length(min = 2)
    @Column(name = "first_name", unique = false, nullable = true, length = 32)
    private String firstName;

    @Length(min = 3)
    @Column(name = "last_name", unique = false, nullable = true, length = 32)
    private String lastName;

    @Column(name = "is_active")
    private Boolean active = true;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "users_roles", joinColumns = {
            @JoinColumn(name = "users_id", referencedColumnName = "id")}, inverseJoinColumns =
            {@JoinColumn(name = "roles_id", referencedColumnName = "id")})
    Set<Role> roles = new HashSet<>();

    public User(User user) {
        id = user.getId();
        username = user.getUsername();
        email = user.getEmail();
        password = user.getPassword();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        active = user.getActive();
        roles = user.getRoles();
    }

    public void addRole(Role role) {
        roles.add(role);
        role.getUsers().add(this);
    }

    public void addRoles(Set<Role> roles) {
        roles.forEach(this::addRole);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        role.getUsers().remove(this);
    }
}
