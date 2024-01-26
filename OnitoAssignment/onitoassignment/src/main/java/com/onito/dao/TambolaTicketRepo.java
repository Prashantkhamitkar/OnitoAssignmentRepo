package com.onito.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onito.entity.TambolaTicket;

@Repository
public interface TambolaTicketRepo extends JpaRepository<TambolaTicket, Long> {

}
