package com.dawidg90.simple_bank.controller;

import com.dawidg90.simple_bank.model.Customer;
import com.dawidg90.simple_bank.model.Loans;
import com.dawidg90.simple_bank.repository.CustomerRepository;
import com.dawidg90.simple_bank.repository.LoanRepository;
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
public class LoansControllerResourceServer {

    private final LoanRepository loanRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/loans")
    public List<Loans> loanDetailsResourceServer(@RequestParam String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        if (customerOptional.isPresent()) {
            return loanRepository.findByCustomerIdOrderByStartDateDesc(customerOptional.get().getId());
        } else {
            return List.of();
        }
    }
}
