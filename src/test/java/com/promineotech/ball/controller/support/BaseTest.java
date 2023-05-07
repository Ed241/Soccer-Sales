package com.promineotech.ball.controller.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import lombok.Getter;

public class BaseTest {
	@LocalServerPort
	public int serverPort;
	
	@Autowired
	@Getter
	private TestRestTemplate restTemplate;
	
	protected String getBaseUriForBalls() {
		return String.format("http://localhost:%d/soccer", serverPort);
	}
	
	protected String getBaseUriForOrders() {
		return String.format("http://localhost:%d/orders", serverPort);
	}
}
