package com.eazybytes.controller;

import java.util.List;

import com.eazybytes.model.Cards;
import com.eazybytes.repository.CardsRepository;
import com.eazybytes.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardsController {

	private final CardsRepository cardsRepository;
	private final CustomerRepository customerRepository;

	@GetMapping("/myCards")
	public List<Cards> getCardDetails(@RequestParam String email) {
		return customerRepository.findByEmail(email).map(
				customer -> cardsRepository
						.findByCustomerId(customer.getId())
		).orElseGet(List::of);
	}

}
