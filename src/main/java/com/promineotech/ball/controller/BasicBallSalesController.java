package com.promineotech.ball.controller;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.ball.entity.Ball;
import com.promineotech.ball.entity.BallType;
import com.promineotech.ball.service.BallSalesService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BasicBallSalesController implements BallSalesController {
	
	@Autowired
	private BallSalesService ballSalesService;
	
	@Override
	public List<Ball> fetchBalls(BallType type, String year) {
		log.debug("type={}, year={}", type, year );
		return ballSalesService.fetchBalls(type, year);
	}

}
