package com.promineotech.ball.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promineotech.ball.entity.Order;
import com.promineotech.ball.entity.OrderRequest;
import com.promineotech.ball.service.BallOrderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/orders")
@Slf4j
public class BasicBallOrderController implements OrderController {
	
	@Autowired
	private BallOrderService ballOrderService;
	
	@Override
	public Order createOrder(OrderRequest orderRequest) {
		log.debug("Order={}", orderRequest);
		
		return ballOrderService.createOrder(orderRequest);
	}

}
