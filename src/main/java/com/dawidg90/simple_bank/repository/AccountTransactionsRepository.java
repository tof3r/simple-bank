package com.dawidg90.simple_bank.repository;

import java.util.List;

import com.dawidg90.simple_bank.model.AccountTransactions;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountTransactionsRepository extends CrudRepository<AccountTransactions, String> {
	
	List<AccountTransactions> findByCustomerIdOrderByTransactionDateDesc(long customerId);

}
