# Spring Cloud RabbitMQ Microservices Example

## API Documentation

### 1. Ticket Service

| Method   | Path                                                            | Description                        | 
|:---------|:----------------------------------------------------------------|:-----------------------------------|
| `POST`   | [`/api/v1/meetups`](#a-create-a-new-meetup)                     | Create a new meetup                |
| `GET`    | [`/api/v1/meetups`](#b-get-details-of-a-particular-meetup)      | Get details of a particular meetup |
| `GET`    | [`/api/v1/meetups`](#c-get-list-of-all-meetup)                  | Get list of all meetups            |
| `PUT`    | [`/api/v1/meetups`](#d-update-a-particular-meetup)              | Update a particular meetup         |
| `DELETE` | [`/api/v1/meetups`](#e-delete-a-particular-meetup)              | Delete a particular meetup         |
| `POST`   | [`/api/v1/tickets`](#f-create-a-new-ticket)                     | Create a new ticket                |  
| `GET`    | [`/api/v1/tickets/{id}`](#g-get-details-of-a-particular-ticket) | Get details of a particular ticket | 
| `GET`    | [`/api/v1/tickets`](#h-get-list-of-all-tickets)                 | Get list of all tickets            | 

### a. Create a new meetup

```
curl -X POST http://localhost:8090/api/v1/meetups \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-d '{"name":"Encryption Security","price":100,"currency":"USD","eventDate":"2020-08-03 12:15","url":"http://www.zoom.us/A8A37N9"}'
```

### b. Get details of a particular meetup

```
curl -X GET http://localhost:8090/api/v1/meetup/fb712cb8-af82-4f4f-98f4-b1327c3458c2 \
-H "Accept: application/json"
```


### f. Create a new ticket

```
curl -X POST http://localhost:8090/api/v1/tickets \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-d '{"meetupId":"fb712cb8-af82-4f4f-98f4-b1327c3458c2","accountId":"cec1e90d-9d6e-4b2d-a15a-c61c3dc9bd46","count":1}'
```

### h. Get details of a particular ticket

```
curl -X GET http://localhost:8090/api/v1/tickets/fb712cb8-af82-4f4f-98f4-b1327c3458c2 \
-H "Accept: application/json"
```

