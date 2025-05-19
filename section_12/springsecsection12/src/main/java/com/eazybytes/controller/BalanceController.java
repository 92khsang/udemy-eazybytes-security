package com.eazybytes.controller;

import java.util.List;

import com.eazybytes.model.AccountTransactions;
import com.eazybytes.repository.AccountTransactionsRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BalanceController {

	private final AccountTransactionsRepository accountTransactionsRepository;

	@GetMapping("/myBalance")
	public List<AccountTransactions> getBalanceDetails(@RequestParam long id) {
		return accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(id);
	}
}
