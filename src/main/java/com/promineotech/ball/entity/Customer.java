package com.promineotech.ball.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
	
	private Long customerPk;
	private String customerId;
	private String firstName;
	private String lastName;
	private String phone;
	
	@JsonIgnore
	public Long getCustomerPk() {
		return customerPk;
	}
	
}
