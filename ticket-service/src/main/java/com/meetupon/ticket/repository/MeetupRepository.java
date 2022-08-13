package com.meetupon.ticket.repository;

import com.meetupon.ticket.model.Meetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetupRepository extends JpaRepository<Meetup, String> {

}
