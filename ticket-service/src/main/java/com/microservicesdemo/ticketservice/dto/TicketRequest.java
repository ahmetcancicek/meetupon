package com.microservicesdemo.ticketservice.dto;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TicketRequest {
    private String accountId;
    private String meetupId;
    private Integer count;
}
