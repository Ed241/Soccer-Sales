package com.promineotech.ball.controller;


import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.promineotech.ball.Constants;
import com.promineotech.ball.entity.Ball;
import com.promineotech.ball.entity.BallType;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

@Validated
@RequestMapping("/soccer")
@OpenAPIDefinition(info = @Info(title = "Soccer Ball Sales"),servers = {
	@Server(url = "http://localhost:8080", description = "Local server")})
public interface BallSalesController {
	
	/**
	 * 
	 */
	
	// @formatter:off
	@Operation(
		summary = "Returns a list of soccer balls",
		description = "Returns a list of soccer balls given an optional ball type and/or year",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "A list of soccer balls is returned",
				content = @Content(
					mediaType = "application/json",
						schema = @Schema(implementation = Ball.class))),
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
			@Parameter(
				name = "ballType",
				allowEmptyValue = false,
				required = false,
				description = "The ballType name (i.e, 'BRAZUCA')"),
			@Parameter(
					name = "year",
					allowEmptyValue = false,
					required = false,
					description = "The year the ball was used (i.e, '2018')"),
		}
	)
	@GetMapping
	@ResponseStatus(code = HttpStatus.OK)
	List<Ball> fetchBalls(
			@RequestParam(required = false)
				BallType type,
			@Length(max = Constants.YEAR_MAX_LENGTH)
			@Pattern(regexp = "\\d+")
			@RequestParam(required = false)
				String year);
	// @formatter:on
}
