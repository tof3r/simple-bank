package com.dawidg90.simple_bank.controller;

import com.dawidg90.simple_bank.model.Loans;
import com.dawidg90.simple_bank.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Profile("auth-and-resource-server")
@RestController
@RequiredArgsConstructor
public class LoansController {

    private final LoanRepository loanRepository;

    @GetMapping("/loans")
    public List<Loans> loanDetails(@RequestParam long id) {
        return loanRepository.findByCustomerIdOrderByStartDateDesc(id);
    }
}
