package com.onito.service;

import java.util.List;

import org.springframework.data.domain.Page;

//import org.hibernate.query.Page;

import org.springframework.data.domain.Pageable;

import com.onito.entity.TambolaTicket;

public interface TambolaTicketService {
List<TambolaTicket> generateTickets(int numberOfSets);
//List<Integer> generateTambolaSets();
List<List<Integer>> generateTicketNumbers();
Page<TambolaTicket> getTickets(Pageable pageable);
}
