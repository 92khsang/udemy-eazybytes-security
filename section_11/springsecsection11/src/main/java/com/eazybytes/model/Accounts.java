package com.eazybytes.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Entity
@Table(name = "accounts")
public class Accounts {

	@Id
	@Column(name = "account_number")
	private long accountNumber;

	@Column(name = "customer_id")
	private long customerId;

	@Column(nullable = false, length = 100)
	private String accountType;

	@Column(nullable = false, length = 200)
	private String branchAddress;

	@CreationTimestamp
	private LocalDate createDt;
}
