package com.eazybytes.repository;

import java.util.Optional;

import com.eazybytes.model.Accounts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {
	Optional<Accounts> findByCustomerId(long customerId);
}