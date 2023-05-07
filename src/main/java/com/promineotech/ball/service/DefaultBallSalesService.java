package com.promineotech.ball.service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promineotech.ball.dao.BallSalesDao;
import com.promineotech.ball.entity.Ball;
import com.promineotech.ball.entity.BallType;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultBallSalesService implements BallSalesService {
	
	@Autowired
	private BallSalesDao ballSalesDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Ball> fetchBalls(BallType type, String year) {
		log.info("The fetchBalls method was called with type={} and year={}", type, year);
		
		List<Ball> balls = ballSalesDao.fetchBalls(type, year);
		
		if(balls.isEmpty()) {
			String msg = String.format("No balls found with type=%s and year=%s", type, year);
			throw new NoSuchElementException(msg);
		}
		
		
		return balls;
	}

}
