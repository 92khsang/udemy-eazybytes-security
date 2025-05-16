package com.eazybytes.repository;

import com.eazybytes.model.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}