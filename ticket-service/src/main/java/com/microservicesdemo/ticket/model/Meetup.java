package com.microservicesdemo.ticket.model;

import com.microservicesdemo.ticket.dto.Currency;
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
@Table(name = "meetup")
public class Meetup extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", unique = true, nullable = false)
    private String id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "price")
    private BigDecimal price;

    @Enumerated
    @Column(name = "currency")
    private Currency currency;

    @Column(nullable = false, name = "event_date")
    private LocalDateTime eventDate;

    @Column(nullable = false, name = "url")
    private String url;

    @Column(name = "user_id")
    private String userId;
}
