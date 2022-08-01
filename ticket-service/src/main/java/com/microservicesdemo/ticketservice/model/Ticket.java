package com.microservicesdemo.ticketservice.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ticket")
public class Ticket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private LocalDateTime reserveDate;

    @Column(nullable = false)
    private BigDecimal price;
}
