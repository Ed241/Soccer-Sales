package com.promineotech.ball.entity;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class OrderRequest {
	
	
	private String customer;
	
	
	private BallType type;
	
	private BallSize size;
	
	private String year;
	
	
	
	
}
