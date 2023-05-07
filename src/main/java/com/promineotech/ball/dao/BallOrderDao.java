package com.promineotech.ball.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.promineotech.ball.entity.Ball;
import com.promineotech.ball.entity.BallSize;
import com.promineotech.ball.entity.BallType;
import com.promineotech.ball.entity.Customer;
import com.promineotech.ball.entity.Order;

public interface BallOrderDao {
	
	
	
	Optional<Customer> fetchCustomer(String customerId);
	
	

	Order saveOrder(Customer customer, Ball ball, BigDecimal price);



	



	Optional<Ball> fetchBall(BallType type, BallSize size, String year);
}
