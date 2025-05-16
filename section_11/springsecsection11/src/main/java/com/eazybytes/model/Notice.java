package com.eazybytes.model;


import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "notice_details")
public class Notice {

	@Id
	@JsonProperty(value = "noticeId")
	@Column(name = "notice_id")
	private long id;

	@Column(nullable = false, length = 200)
	private String noticeSummary;

	@Column(nullable = false, length = 500)
	private String noticeDetails;

	@JsonProperty(value = "noticBegDt")
	@Column(name = "notic_beg_dt", nullable = false)
	private Date noticeBegDt;

	@JsonProperty(value = "noticEndDt")
	@Column(name = "notic_end_dt")
	private Date noticeEndDt;

	@JsonIgnore
	private Date createDt;

	@JsonIgnore
	private Date updateDt;
}
