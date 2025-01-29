package com.dawidg90.simple_bank.controller;

import com.dawidg90.simple_bank.model.AccountTransactions;
import com.dawidg90.simple_bank.model.Customer;
import com.dawidg90.simple_bank.repository.AccountTransactionsRepository;
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
public class BalanceControllerResourceServer {

    private final AccountTransactionsRepository accountTransactionsRepository;
    private final CustomerRepository customerRepository;

    @GetMapping("/balance")
    public List<AccountTransactions> balanceDetailsResourceServer(@RequestParam String email) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(email);
        return customerOptional.map(customer -> accountTransactionsRepository.findByCustomerIdOrderByTransactionDateDesc(customer.getId())).orElse(null);
    }
}
