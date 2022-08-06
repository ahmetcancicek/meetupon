package com.microservicesdemo.ticket.repository;

import com.microservicesdemo.ticket.model.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetupRepository extends JpaRepository<Meetup, String> {

}
