package com.microservicesdemo.ticketservice.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TicketRequest {

    @NotNull
    @Positive
    private String accountId;

    @NotNull
    @Positive
    private String meetupId;

    @NotNull
    @Positive
    private Integer count;
}
