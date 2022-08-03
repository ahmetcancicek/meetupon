# Spring Cloud RabbitMQ Microservices Example

## API Documentation

## 1. Ticket Service

### a. Create a new meetup

You can send a POST request to create a new ticket and returns a new ticket information.

```
Method: POST
URL: /api/v1/tickets
Content-Type: application/json
Accept: application/json
```

#### Parameters :

| Parameter   | Type      | Description                      |
|:------------|:----------|:---------------------------------|
| `name`      | `string`  | **Required**. Name of Meetup     |
| `price`     | `boolean` | **Required**. Price of Meetup    |
| `currency`  | `string`  | **Required**. Currency of Meetup |
| `eventDate` | `string`  | **Required**. Date of Meetup     |
| `url`       | `string`  | **Required**. Url of Meetup      |

#### Example :

* Request :

```
curl -X POST http://localhost:8090/api/v1/meetups \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-d '{"name":"Encryption Security","price":100,"currency":"USD","eventDate":"2020-08-03 12:15","url":"http://www.zoom.us/A8A37N9"}'
```

* Response :

```json

```

### b. Create a new ticket

```
Method: POST
URL: /api/v1/tickets
Content-Type: application/json
Accept: application/json
```

#### Parameters :

| Parameter   | Type      | Description                        |
|:------------|:----------|:-----------------------------------|
| `meetupId`  | `string`  | **Required**. Id of Meetup         |
| `accountId` | `string`  | **Required**. Account Id of Meetup |
| `count`     | `numeric` | **Required**. Count of Meetup      |

#### Example :

* Request :

```
curl -X POST http://localhost:8090/api/v1/tickets \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-d '{"meetupId":"fb712cb8-af82-4f4f-98f4-b1327c3458c2","accountId":"cec1e90d-9d6e-4b2d-a15a-c61c3dc9bd46","count":1}'
```

* Response:

```json

```
