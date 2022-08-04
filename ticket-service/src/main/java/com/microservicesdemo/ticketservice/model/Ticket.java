package com.microservicesdemo.ticketservice.model;

import com.microservicesdemo.ticketservice.dto.Currency;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "ticket")
public class Ticket extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(nullable = false)
    private String accountId;

    @ManyToOne
    @JoinColumn(name = "meetup_id", nullable = false)
    private Meetup meetup;


    @Column(nullable = false)
    private Integer count;

    @Column(nullable = false)
    private LocalDateTime reserveDate;

    @Enumerated
    private Currency currency;

    @Column(nullable = false)
    private BigDecimal price;


    public BigDecimal findTotalPrice() {
        return this.getPrice().multiply(BigDecimal.valueOf(this.getCount()));
    }
}
