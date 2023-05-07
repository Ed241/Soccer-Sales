package com.promineotech.ball.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import com.promineotech.ball.controller.support.CreateOrderTestSupport;
import com.promineotech.ball.entity.BallSize;
import com.promineotech.ball.entity.BallType;
import com.promineotech.ball.entity.Order;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:flyway\\migrations\\V1.0__Soccer_Schema.sql",
		"classpath:flyway\\migrations\\V1.1__Soccer_Data.sql"}, 
		config = @SqlConfig(encoding = "utf-8"))
class CreateOrderTest extends CreateOrderTestSupport {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Test
	void testCreateOrderReturnsSuccess201() {
		// Given: an order as JSON
		String body = createOrderBody();
		String uri = getBaseUriForOrders();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> bodyEntity = new HttpEntity<>(body, headers);
		// When: the order is sent
		
		ResponseEntity<Order> response = getRestTemplate().exchange(uri, HttpMethod.POST, bodyEntity, Order.class);
		
		// Then: a 201 status is returned
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		
		// And: the returned order is correct
		assertThat(response.getBody()).isNotNull();
		
		Order order = response.getBody();
		assertThat(order.getCustomer().getCustomerId()).isEqualTo("SMITH_JOHN");
		assertThat(order.getBall().getBallId()).isEqualTo("BRAZUCA");
		assertThat(order.getBall().getYear()).isEqualTo("2014");
		assertThat(order.getBall().getBallSize()).isEqualTo("SIZE5");
		
	}

}
