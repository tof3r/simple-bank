package com.dawidg90.simple_bank.controller;

import com.dawidg90.simple_bank.model.Accounts;
import com.dawidg90.simple_bank.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile("auth-and-resource-server")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountsRepository accountsRepository;

    @GetMapping("/account")
    public Accounts accountDetails(@RequestParam long id) {
        return accountsRepository.findByCustomerId(id);
    }
}
