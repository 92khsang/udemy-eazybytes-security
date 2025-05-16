package com.eazybytes.controller;

import java.util.List;

import com.eazybytes.model.Cards;
import com.eazybytes.repository.CardsRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardsController {

	private final CardsRepository cardsRepository;

	@GetMapping("/myCards")
	public List<Cards> getCardDetails(@RequestParam long id) {
		return cardsRepository.findByCustomerId(id);
	}

}
