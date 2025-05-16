package com.eazybytes.model;

import java.time.LocalDate;

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
@Table(name = "cards")
public class Cards {

	@Id
	@Column(name = "card_id")
	private long id;

	@Column(nullable = false, length = 100)
	private String cardNumber;

	@Column(name = "customer_id")
	private long customerId;

	@Column(nullable = false, length = 100)
	private String cardType;

	@Column(nullable = false)
	private Integer totalLimit;

	@Column(nullable = false)
	private Integer amountUsed;

	@Column(nullable = false)
	private Integer availableAmount;

	@CreationTimestamp
	private LocalDate createDt;
}
