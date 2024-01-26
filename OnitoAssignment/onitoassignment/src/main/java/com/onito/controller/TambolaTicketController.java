package com.onito.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onito.dto.RequestDto;
import com.onito.emailservices.EmailServices;
import com.onito.entity.TambolaTicket;
import com.onito.service.TambolaTicketService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/ticket")
@CrossOrigin("*")
@Slf4j
public class TambolaTicketController {
	@Autowired
	private EmailServices emailservices;
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
	@PostMapping("/sendmail")
	public ResponseEntity<?> sendGeneratedTicket(@RequestBody @Valid RequestDto dto) throws MessagingException, IOException{
		int numberofset=(int)dto.getNumber();
		List<TambolaTicket> generatedTickets=service.generateTickets(numberofset);
		List<List<Integer>> ticketData = new ArrayList();
	    for (TambolaTicket ticket : generatedTickets) {
	        ticketData.addAll(ticket.getTicketNumbers());
	    }
		emailservices.sendEmailWithAttachment(dto.getEmail(),ticketData);
		return ResponseEntity.ok("Generated Ticket sent to UsersEmail");
	}
}
