package com.meetupon.ticketservice.integration.repository;

import com.meetupon.ticket.model.Meetup;
import com.meetupon.ticket.model.Ticket;
import com.meetupon.ticket.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Sql(scripts = "classpath:sql/ticket.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TicketRepositoryIT extends AbstractIT {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void shouldSaveMeetup() {
        Meetup meetup = Meetup.builder().id("36a57006-1362-11ed-8887-0242ac150002").build();

        Ticket ticket = Ticket.builder()
                .accountId(UUID.randomUUID().toString())
                .count(1)
                .price(BigDecimal.TEN)
                .reserveDate(LocalDateTime.now())
                .meetup(meetup)
                .build();

        Ticket createdTicket = ticketRepository.save(ticket);
        Ticket expectedTicket = testEntityManager.find(Ticket.class, createdTicket.getId());

        assertEquals(ticket, expectedTicket, "Objects must be identical");
    }
}
