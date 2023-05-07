package com.promineotech.ball.controller.support;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.promineotech.ball.entity.Ball;
import com.promineotech.ball.entity.BallSize;
import com.promineotech.ball.entity.BallType;

public class FetchBallTestSupport extends BaseTest {
	
	protected List<Ball> ballExpected(){
		List<Ball> list = new LinkedList<>();
		// @formatter:off
		list.add(Ball.builder()
				.ballId(BallType.ADIDAS_FINALE_ISTANBUL)
				.year("2021")
				.ballSize(BallSize.SIZE5)
				.price(new BigDecimal("50.00"))
				.build());
		
		list.add(Ball.builder()
				.ballId(BallType.ADIDAS_FINALE_ISTANBUL)
				.year("2021")
				.ballSize(BallSize.SIZE4)
				.price(new BigDecimal("40.00"))
				.build());
		
		list.add(Ball.builder()
				.ballId(BallType.ADIDAS_FINALE_ISTANBUL)
				.year("2021")
				.ballSize(BallSize.SIZE3)
				.price(new BigDecimal("30.00"))
				.build());
		// @formatter:on
		
		
		return list;
	}
	
	protected void assertErrorMessageValid(Map<String, Object> error, HttpStatus status) {
		// @formatter:off
		assertThat(error)
			.containsKey("message")
			.containsEntry("status code", status.value())
			.containsEntry("uri", "/soccer")
			.containsKey("timestamp")
			.containsEntry("reason", status.getReasonPhrase());
		// @formatter:on
	}
}
