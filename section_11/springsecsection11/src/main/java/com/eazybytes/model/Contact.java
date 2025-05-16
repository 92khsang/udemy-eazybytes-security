package com.eazybytes.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "contact_messages")
public class Contact {

	@Id
	@JsonProperty(value = "contactId")
	@Column(name = "contact_id", length = 50)
	private String id;

	@Column(nullable = false, length = 50)
	private String contactName;

	@Column(nullable = false, length = 100)
	private String contactEmail;

	@Column(nullable = false, length = 500)
	private String subject;

	@Column(nullable = false, length = 2000)
	private String message;

	@Column(name = "create_dt")
	private Date createDt;
}
