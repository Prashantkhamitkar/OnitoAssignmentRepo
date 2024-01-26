package com.onito.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onito.entity.TambolaTicket;
import com.onito.service.TambolaTicketService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin("*")
@Slf4j
public class TambolaTicketController {
@Autowired
private TambolaTicketService service;
@PostMapping("/generate/{numberOfSets}")
public ResponseEntity<?> generateTambolaTickets(@PathVariable int numberOfSets){
	List<TambolaTicket> generatedTickets=service.generateTickets(numberOfSets);
	return ResponseEntity.ok(generatedTickets);
}
@GetMapping("/fetch")
public ResponseEntity<Page<TambolaTicket>> fetchTambolaTickets(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
    Page<TambolaTicket> tickets = service.getTickets(PageRequest.of(page, size));
    return new ResponseEntity<>(tickets, HttpStatus.OK);
}
	
}
