package com.promineotech.ball.dao;

import java.util.List;

import com.promineotech.ball.entity.Ball;
import com.promineotech.ball.entity.BallType;

public interface BallSalesDao {
	
	/**
	 * 
	 * @param type
	 * @param year
	 * @return
	 */
	List<Ball> fetchBalls(BallType type, String year);
}
