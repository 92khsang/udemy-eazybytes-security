package com.eazybytes.authserver.repository;

import java.util.Optional;

import com.eazybytes.authserver.model.Customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Optional<Customer> findByEmail(String email);

}
