package com.dawidg90.simple_bank.controller;

import com.dawidg90.simple_bank.model.AccountTransactions;
import com.dawidg90.simple_bank.repository.AccountTransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Profile("auth-and-resource-server")
@RestController
@RequiredArgsConstructor
public class BalanceController {

    private final AccountTransactionsRepository accountTransactionsRepository;

    @GetMapping("/balance")
    public List<AccountTransactions> balanceDetails(@RequestParam long id) {
        return accountTransactionsRepository.findByCustomerIdOrderByTransactionDateDesc(id);
    }
}
