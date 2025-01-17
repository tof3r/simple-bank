package com.dawidg90.simple_bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

    @GetMapping("/loans")
    public String loansPage() {
        return "Loans page";
    }
}
