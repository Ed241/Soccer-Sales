package com.promineotech.ball.controller;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.promineotech.ball.Constants;
import com.promineotech.ball.entity.Ball;
import com.promineotech.ball.entity.BallType;
import com.promineotech.ball.entity.Order;
import com.promineotech.ball.entity.OrderRequest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/orders")
@OpenAPIDefinition(info = @Info(title = "Soccer Ball Orders"),servers = {
	@Server(url = "http://localhost:8080", description = "Local server")})
public interface OrderController {
	
	/**
	 * 
	 */
	
	// @formatter:off
	@Operation(
		summary = "Create an order of soccer balls",
		description = "Returns the created order",
		responses = {
			@ApiResponse(
				responseCode = "201",
				description = "The created order is returned",
				content = @Content(
					mediaType = "application/json",
						schema = @Schema(implementation = Order.class))),
			@ApiResponse(
				responseCode = "400",
				description = "The request Parameters are invalid",
				content = @Content(
					mediaType = "application/json")),
			@ApiResponse(
				responseCode = "404",
				description = "No soccer balls were found with the input criteria",
				content = @Content(
					mediaType = "application/json")),
			@ApiResponse(
				responseCode = "500",
				description = "An unplanned error occurred.",
				content = @Content(
						mediaType = "application/json")
					)
			
		},
		parameters = {
			@Parameter(name = "orderRequest",
				required = true, 
				description = "The order as JSON")
		}
	)
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	Order createOrder(@RequestBody OrderRequest orderRequest);
	// @formatter:on
}
