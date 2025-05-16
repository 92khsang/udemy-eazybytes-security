package com.eazybytes.controller;

import java.util.List;

import com.eazybytes.model.Loans;
import com.eazybytes.repository.LoansRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoansController {

	private final LoansRepository loanRepository;

	@GetMapping("/myLoans")
	public List<Loans> getLoanDetails(@RequestParam long id) {
		return loanRepository.findByCustomerIdOrderByStartDtDesc(id);
	}

}
