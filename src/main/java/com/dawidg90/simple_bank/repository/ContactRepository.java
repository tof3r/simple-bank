package com.dawidg90.simple_bank.repository;

import com.dawidg90.simple_bank.model.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends CrudRepository<Contact, String> {
	
	
}
