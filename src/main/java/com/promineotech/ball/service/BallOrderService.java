package com.promineotech.ball.service;

import com.promineotech.ball.entity.Order;
import com.promineotech.ball.entity.OrderRequest;

public interface BallOrderService {
	
	Order createOrder(OrderRequest orderRequest);
}
