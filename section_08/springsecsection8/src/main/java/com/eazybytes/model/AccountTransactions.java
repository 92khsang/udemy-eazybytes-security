package com.eazybytes.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Setter
@Getter
@Entity
@Table(name = "account_transactions")
public class AccountTransactions {

	@Id
	@JsonProperty(value = "transactionId")
	@Column(name = "transaction_id", length = 200)
	private String id;

	@Column(name = "account_number")
	private long accountNumber;

	@Column(name = "customer_id")
	private long customerId;

	@Column(nullable = false)
	private LocalDate transactionDt;

	@Column(nullable = false, length = 200)
	private String transactionSummary;

	@Column(nullable = false, length = 100)
	private String transactionType;

	@Column(nullable = false)
	private Integer transactionAmt;

	@Column(nullable = false)
	private Integer closingBalance;

	@CreationTimestamp
	private LocalDate createDt;
}
