# Meetupon - Microservices Example

This repository contains an example that is developing with microservices architecture for the platform named to aim to
provide services among people to have a connection.

## Contents

- [`Installation`](#installation)
- [`Development`](#development)
- [`Roadmap`](#roadmap)
- [`Contributing`](#contributing)
- [`License`](#license)

## Installation

```bash
cd registry-server
mvn clean install -Dmaven.test.skip=true
cd ..
cd config-server
mvn clean install -Dmaven.test.skip=true
cd ..
cd gateway-service
mvn clean install -Dmaven.test.skip=true
cd ..
cd auth-service
mvn clean install -Dmaven.test.skip=true
cd ..
docker-compose up -d --build
```

## Development

To build and run the application

```bash

```

## Technologies Used

This project is developing with java programming languages and java technologies to create an application that contains microservices architecture. You can see the list of technologies that are used for the application below.

1. Spring Boot
2. Spring Cloud
3. Spring Data
4. Hibernate
5. Java 17
6. MySQL
7. Map Struct
8. Docker
9. Maven 3
10. Keycloak
11. Zipkin
12. Logstash
13. Elasticsearch

## Roadmap

- [X] Registry Server
- [X] Config Server
- [X] Gateway Service
- [X] Authentication Service
- [ ] Ticket Service


## Contributing

If you have a suggestion that would make this better, please fork the repository and create a pull request.

1- Fork the Project

2- Create your dev branch (```git checkout -b dev```)

3- Commit your Changes (```git commit -m 'Add some feature'```)

4- Push to the Branch (git push origin dev)

5- Open a Pull Request

## License

Distributed under the GNU License. See LICENSE.md for more information.

[![GPLv3 License](https://img.shields.io/badge/License-GPL%20v3-yellow.svg)](https://opensource.org/licenses/)