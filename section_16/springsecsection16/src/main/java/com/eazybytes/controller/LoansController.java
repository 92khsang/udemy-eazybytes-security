package com.eazybytes.controller;

import java.util.List;

import com.eazybytes.model.Loans;
import com.eazybytes.repository.CustomerRepository;
import com.eazybytes.repository.LoansRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoansController {

	private final LoansRepository loanRepository;
	private final CustomerRepository customerRepository;

	@GetMapping("/myLoans")
	@PostAuthorize("hasRole('USER')")
	public List<Loans> getLoanDetails(@RequestParam String email) {
		return customerRepository.findByEmail(email).map(
				customer -> loanRepository.findByCustomerIdOrderByStartDtDesc(customer.getId())
		).orElseGet(List::of);
	}

}
