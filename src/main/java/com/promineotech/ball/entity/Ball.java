package com.promineotech.ball.entity;

import java.math.BigDecimal;
import java.util.Comparator;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ball implements Comparable<Ball>{
	private Long ballPk;
    private BallType ballId;
	private BallSize ballSize;
	
	@Pattern(regexp = "\\d+", message = "Year must only contain digits")
	private String year;
	private BigDecimal price;
	
	
	@JsonIgnore
	public Long getBallPk() {
		return ballPk;
	}
	
	
	public int compareTo(Ball that) {
		// @formatter:off
		return Comparator
				.comparing(Ball::getBallId)
				.thenComparing(Ball::getYear)
				.thenComparing(Ball::getBallSize)
				.compare(this, that);
		// @formatter:on
	}
}
