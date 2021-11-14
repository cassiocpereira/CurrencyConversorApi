package com.conversor.currency.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@Column(name = "origin_currency", length = 3, nullable = false)
	private String originCurrency;
	
	@Column(name = "destination_currency", length = 3, nullable = false)
	private String destinationCurrency;
	
	@Column(name = "origin_value", nullable = false)
	private Double originValue;
	
	@Column(name = "destination_value", nullable = false)
	private Double destinationValue;
	
	@Column(name = "exchange_rate", nullable = false)
	private Double exchangeRate;
	
	@Column(name = "transaction_date", nullable = false)
	private Date transactionDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOriginCurrency() {
		return originCurrency;
	}

	public void setOriginCurrency(String originCurrency) {
		this.originCurrency = originCurrency;
	}

	public String getDestinationCurrency() {
		return destinationCurrency;
	}

	public void setDestinationCurrency(String destinationCurrency) {
		this.destinationCurrency = destinationCurrency;
	}

	public Double getOriginValue() {
		return originValue;
	}

	public void setOriginValue(Double originValue) {
		this.originValue = originValue;
	}

	public Double getDestinationValue() {
		return destinationValue;
	}

	public void setDestinationValue(Double destinationValue) {
		this.destinationValue = destinationValue;
	}

	public Double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
}
