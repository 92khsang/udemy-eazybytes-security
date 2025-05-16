package com.eazybytes.repository;

import java.util.List;

import com.eazybytes.model.Cards;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardsRepository extends JpaRepository<Cards, Long> {
	List<Cards> findByCustomerId(long customerId);
}