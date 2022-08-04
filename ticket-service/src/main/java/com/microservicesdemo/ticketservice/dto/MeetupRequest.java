package com.microservicesdemo.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MeetupRequest {

    @NotNull
    private String name;

    @NotNull
    private Currency currency;

    @NotNull
    @Positive
    private BigDecimal price;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime eventDate;

    @NotNull
    private String url;
}
