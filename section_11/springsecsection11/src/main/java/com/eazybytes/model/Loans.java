package com.eazybytes.model;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "loans")
public class Loans {

	@Id
	@Column(name = "loan_number")
	private long loanNumber;

	@Column(name = "customer_id")
	private long customerId;

	@Column(nullable = false)
	private LocalDate startDt;

	@Column(nullable = false, length = 100)
	private String loanType;

	@Column(nullable = false)
	private Integer totalLoan;

	@Column(nullable = false)
	private Integer amountPaid;

	@Column(nullable = false)
	private Integer outstandingAmount;

	private Date createDt;
}
