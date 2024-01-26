package com.onito.service;

import com.onito.dao.TambolaTicketRepo;
import com.onito.entity.TambolaTicket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class TambolaTicketServiceImpl implements TambolaTicketService {

    @Autowired
    private TambolaTicketRepo tambolaRepo;

    @Override
    public List<TambolaTicket> generateTickets(int numberOfSets) {
        log.info("Generating {} sets of Tambola tickets", numberOfSets);
        List<TambolaTicket> generatedTickets = new ArrayList<>();
        for (int i = 0; i < numberOfSets; i++) {
            List<TambolaTicket> tambolaSet = generateTambolaSet();
            generatedTickets.addAll(tambolaSet);
        }
        log.info("Saving generated Tambola tickets to the database");
        return tambolaRepo.saveAll(generatedTickets);
    }

    private List<TambolaTicket> generateTambolaSet() {
        List<TambolaTicket> tambolaSet = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            TambolaTicket ticket = new TambolaTicket();
            ticket.setTicketNumbers(generateTicketNumbers());
            tambolaSet.add(ticket);
        }
        return tambolaSet;
    }

    public List<List<Integer>> generateTicketNumbers() {
        List<List<Integer>> ticketNumbers = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<Integer> row = generateRow();
            ticketNumbers.add(row);
        }
        return ticketNumbers;
    }

    private List<Integer> generateRow() {
        List<Integer> numbers = new ArrayList<>();
        List<Integer> numberPool = new ArrayList<>();
        for (int i = 1; i <= 90; i++) {
            numberPool.add(i);
        }
        Collections.shuffle(numberPool);
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (count < 5) {
                numbers.add(numberPool.get(i));
                count++;
            } else {
                numbers.add(0);
            }
        }
        Collections.shuffle(numbers);
       
        for (int i = 0; i < numbers.size() - 3; i++) {
            if (numbers.get(i) != 0 && numbers.get(i + 1) != 0 && numbers.get(i + 2) != 0 && numbers.get(i + 3) != 0) {
               
                for (int j = numbers.size() - 1; j >= 0; j--) {
                    if (numbers.get(j) == 0) {
                        for (int k = i + 3; k >= i; k--) {
                            if (numbers.get(k) != 0) {
                                numbers.set(j, numbers.get(k));
                                numbers.set(k, 0);
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        return numbers;
    }

    @Override
    public Page<TambolaTicket> getTickets(Pageable pageable) {
        log.info("Fetching Tambola tickets from the database");
        return tambolaRepo.findAll(pageable);
    }
}
