CREATE DATABASE users;

USE users;

create table role
(
    id   varchar(255) not null
        primary key,
    name varchar(255) not null,
    constraint UK_8sewwnpamngi6b1dwaa88askk
        unique (name)
);

create table users
(
    id         varchar(255) not null
        primary key,
    created_at datetime(6) not null,
    updated_at datetime(6) null,
    is_active  bit null,
    email      varchar(100) not null,
    first_name varchar(32) null,
    last_name  varchar(32) null,
    password   varchar(255) not null,
    username   varchar(20)  not null,
    constraint UK_6dotkott2kjsp8vw4d0m25fb7
        unique (email),
    constraint UK_r43af9ap4edm43mmtq01oddj6
        unique (username)
);

create table users_roles
(
    users_id varchar(255) not null,
    roles_id varchar(255) not null,
    primary key (users_id, roles_id),
    constraint FK15d410tj6juko0sq9k4km60xq
        foreign key (roles_id) references role (id),
    constraint FKml90kef4w2jy7oxyqv742tsfc
        foreign key (users_id) references users (id)
);

