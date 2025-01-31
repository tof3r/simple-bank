package com.dawidg90.simple_bank.controller;

import com.dawidg90.simple_bank.model.Cards;
import com.dawidg90.simple_bank.repository.CardsRepository;
import com.dawidg90.simple_bank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Profile("auth-and-resource-server")
@RestController
@RequiredArgsConstructor
public class CardsController {

    private final CardsRepository cardsRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/cards")
    public List<Cards> cardsDetails(@RequestParam long id) {
        return cardsRepository.findByCustomerId(id);
    }
}
