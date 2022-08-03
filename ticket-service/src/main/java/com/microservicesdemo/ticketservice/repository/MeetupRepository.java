package com.microservicesdemo.ticketservice.repository;

import com.microservicesdemo.ticketservice.model.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetupRepository extends JpaRepository<Meetup, String> {

}
