package com.eazybytes.controller;

import com.eazybytes.model.Accounts;
import com.eazybytes.repository.AccountsRepository;
import com.eazybytes.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

	private final AccountsRepository accountsRepository;
	private final CustomerRepository customerRepository;

	@GetMapping("/myAccount")
	public Accounts getAccountDetails(@RequestParam String email) {
		return customerRepository.findByEmail(email)
				.map(customer -> accountsRepository.findByCustomerId(customer.getId())
						.orElseGet(() -> null))
				.orElse(null);
	}

}
