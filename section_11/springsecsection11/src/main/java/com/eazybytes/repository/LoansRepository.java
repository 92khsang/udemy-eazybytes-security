package com.eazybytes.repository;

import java.util.List;

import com.eazybytes.model.Loans;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoansRepository extends JpaRepository<Loans, Long> {
	List<Loans> findByCustomerIdOrderByStartDtDesc(Long customerId);
}