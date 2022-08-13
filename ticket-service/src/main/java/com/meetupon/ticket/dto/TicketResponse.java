package com.meetupon.ticket.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private String id;
    private String meetupId;
    private String accountId;
    private Integer count;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime reserveDate;
    private Currency currency;
    private BigDecimal price;
}
