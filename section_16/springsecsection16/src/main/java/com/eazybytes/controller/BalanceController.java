package com.eazybytes.controller;

import java.util.List;

import com.eazybytes.model.AccountTransactions;
import com.eazybytes.repository.AccountTransactionsRepository;
import com.eazybytes.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BalanceController {

	private final AccountTransactionsRepository accountTransactionsRepository;
	private final CustomerRepository customerRepository;

	@GetMapping("/myBalance")
	public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {
		return customerRepository.findByEmail(email).map(
				customer -> accountTransactionsRepository
						.findByCustomerIdOrderByTransactionDtDesc(customer.getId())
		).orElseGet(List::of);
	}
}
