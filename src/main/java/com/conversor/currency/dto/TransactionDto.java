package com.conversor.currency.dto;

public class TransactionDto {

	private Long userId;
	private String originCurrency;
	private String destinationCurrency;
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
