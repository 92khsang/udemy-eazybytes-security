package com.eazybytes.repository;

import java.util.List;

import com.eazybytes.model.Loans;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public interface LoansRepository extends JpaRepository<Loans, Long> {

//	@PreAuthorize("hasRole('ROOT')")
	List<Loans> findByCustomerIdOrderByStartDtDesc(Long customerId);
}