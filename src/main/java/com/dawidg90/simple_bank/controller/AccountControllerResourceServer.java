package com.dawidg90.simple_bank.controller;

import com.dawidg90.simple_bank.model.Accounts;
import com.dawidg90.simple_bank.model.Customer;
import com.dawidg90.simple_bank.repository.AccountsRepository;
import com.dawidg90.simple_bank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Profile({"keycloak-auth-server", "spring-auth-server"})
@RestController
@RequiredArgsConstructor
public class AccountControllerResourceServer {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/account")
    public Accounts accountDetailsResourceServer(@RequestParam String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        return customerOptional.map(customer -> accountsRepository.findByCustomerId(customer.getId())).orElse(null);
    }
}
