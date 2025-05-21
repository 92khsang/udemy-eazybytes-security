package com.eazybytes.repository;

import java.util.List;

import com.eazybytes.model.AccountTransactions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionsRepository extends JpaRepository<AccountTransactions, String> {
	List<AccountTransactions> findByCustomerIdOrderByTransactionDtDesc(long customerId);
}