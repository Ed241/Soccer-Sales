package com.promineotech.ball.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promineotech.ball.dao.BallOrderDao;
import com.promineotech.ball.entity.Ball;
import com.promineotech.ball.entity.BallSize;
import com.promineotech.ball.entity.BallType;
import com.promineotech.ball.entity.Customer;
import com.promineotech.ball.entity.Order;
import com.promineotech.ball.entity.OrderRequest;

@Service
public class DefaultBallOrderService implements BallOrderService {
	
	@Autowired
	private BallOrderDao ballOrderDao;
	
	
	@Transactional
	@Override
	public Order createOrder(OrderRequest orderRequest) {
		Customer customer = getCustomer(orderRequest);
		Ball ball = getBall(orderRequest);
		
		BigDecimal total = ball.getPrice();
		
		
		
		return ballOrderDao.saveOrder(customer, ball, total);
		
	}
	
	protected Ball getBall(OrderRequest orderRequest) {
		return ballOrderDao.fetchBall(orderRequest.getType(), orderRequest.getSize(), orderRequest.getYear()).orElseThrow(() -> 
			new NoSuchElementException("Ball with type=" + orderRequest.getType() + ", size=" + orderRequest.getSize()  + ", year=" + orderRequest.getYear()+ " was not found"));
	}
	/**
	 * 
	 * @param orderRequest
	 * @return
	 */
	protected Customer getCustomer(OrderRequest orderRequest) {
		return ballOrderDao.fetchCustomer(orderRequest.getCustomer()).orElseThrow(() -> new NoSuchElementException("Customer with ID=" + orderRequest.getCustomer() + " was not found"));
	}

}
