package com.dawidg90.simple_bank.controller;

import com.dawidg90.simple_bank.model.Cards;
import com.dawidg90.simple_bank.model.Customer;
import com.dawidg90.simple_bank.repository.CardsRepository;
import com.dawidg90.simple_bank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Profile("keycloak")
@RestController
@RequiredArgsConstructor
public class CardsControllerResourceServer {

    private final CardsRepository cardsRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/cards")
    public List<Cards> cardsDetailsResourceServer(@RequestParam String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        return customerOptional.map(customer -> cardsRepository.findByCustomerId(customerOptional.get().getId())).orElse(null);
    }
}
