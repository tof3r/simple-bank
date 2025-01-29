package com.dawidg90.simple_bank.controller;

import com.dawidg90.simple_bank.model.Contact;
import com.dawidg90.simple_bank.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;

@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContactRepository contactRepository;

    @PostMapping("/contact")
//    @PreFilter("filterObject.contactName != 'Test'")
    @PostFilter("filterObject.contactName != 'Test'")
    public List<Contact> saveContactDetails(@RequestBody List<Contact> contacts) {
        List<Contact> returnContacts = new ArrayList<>();
        if (!contacts.isEmpty()) {
            Contact contact = contacts.getFirst();
            contact.setContactId(getServiceRequestNumber());
            contact.setCreateDate(new Date(System.currentTimeMillis()));
            Contact savedContact = contactRepository.save(contact);
            returnContacts.add(savedContact);
        }
        return returnContacts;
    }

    private String getServiceRequestNumber() {
        RandomGenerator generator = RandomGenerator.getDefault();
        int generated = generator.nextInt(999_999_999 - 9_999) + 9_999;
        return "SR" + generated;
    }
}
