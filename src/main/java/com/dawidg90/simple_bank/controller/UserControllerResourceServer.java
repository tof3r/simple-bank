package com.dawidg90.simple_bank.controller;

import com.dawidg90.simple_bank.model.Customer;
import com.dawidg90.simple_bank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Profile("keycloak")
@RestController
@RequiredArgsConstructor
public class UserControllerResourceServer {
    private final CustomerRepository customerRepository;

    @RequestMapping("/user")
    public Customer getUSerDetailsAfterLogin(Authentication authentication) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }
}
