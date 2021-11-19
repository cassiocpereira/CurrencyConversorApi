package com.conversor.currency.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

public class TransactionDto {
	
	@NotNull
	private Long userId;
	@NotNull @NotEmpty @Length(min = 3, max = 3 )
	private String originCurrency;
	@NotNull @NotEmpty @Length(min = 3, max = 3 )
	private String destinationCurrency;
	@NotNull
	private Double originValue;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
}
