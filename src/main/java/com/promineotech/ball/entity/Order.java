package com.promineotech.ball.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Order {
	
	private Long orderPk;
	private Customer customer;
	private Ball ball;
	private BigDecimal total;
	
	
	@JsonIgnore
	public Long getOrderPk() {
		return orderPk;
	}
}
