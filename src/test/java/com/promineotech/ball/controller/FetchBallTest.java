package com.promineotech.ball.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doThrow;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.params.provider.Arguments.arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import com.promineotech.ball.Constants;
import com.promineotech.ball.controller.support.FetchBallTestSupport;
import com.promineotech.ball.entity.Ball;
import com.promineotech.ball.entity.BallType;
import com.promineotech.ball.service.BallSalesService;


class FetchBallTest extends FetchBallTestSupport {
	
	
	@Nested
	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
	@ActiveProfiles("test")
	@Sql(scripts = {"classpath:flyway\\migrations\\V1.0__Soccer_Schema.sql",
			"classpath:flyway\\migrations\\V1.1__Soccer_Data.sql"}, 
			config = @SqlConfig(encoding = "utf-8"))
	class TestsThatDoNotPolluteTheApplicationContext extends FetchBallTestSupport{
		@Autowired
		private TestRestTemplate restTemplate;

		@Test
		void testThatBallsAreReturnedWhenAValidBallTypeAndYearAreSupplied() {
			// Given: a valid ball type, year, and URI
			
			BallType type = BallType.ADIDAS_FINALE_ISTANBUL;
			String year = "2021";
			String uri = 
					String.format("http://localhost:%d/soccer?type=%s&year=%s", serverPort, type, year);
			// When: a connection is made to the URI
			
			ResponseEntity<List<Ball>> response = 
					restTemplate.exchange(uri, HttpMethod.GET,null, new ParameterizedTypeReference<>() {});
			// Then: a success (OK - 200) status code is returned
			
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
			
			// And: the actual list returned is the same as the expected list
			List<Ball> actual = response.getBody();
			List<Ball> expected = ballExpected();
			
			assertThat(actual).isEqualTo(expected);
		}
		
		@Test
		void testThatAnErrorMessageisReturnedWhenUnknownYearIsSupplied() {
			//Given: a valid type, year, and URI
			
			BallType type = BallType.ADIDAS_FINALE_ISTANBUL;
			String year = "6431";
			String uri = 
					String.format("http://localhost:%d/soccer?type=%s&year=%s", serverPort, type, year);
			
			// When: a connection is made to the URI
			
			ResponseEntity<Map<String, Object>> response = 
					restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
			
			// Then: a not found (404) status code is returned
			
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
			
			// And: an error message is returned
			
			Map<String, Object> error = response.getBody();
			assertErrorMessageValid(error, HttpStatus.NOT_FOUND);
		}
		
		@ParameterizedTest
		@MethodSource("com.promineotech.ball.controller.FetchBallTest#parametersForInvalidInput")
		void testThatAnErrorMessageIsReturnedWhenAnInvalidYearIsSupplied(String type, String year, String reason) {
			
			// Given: a valid model, trim and URI
			
			String uri = 
					String.format("http://localhost:%d/soccer?type=%s&year=%s", serverPort, type, year);
			
			// When: a connection is made to the URI
			
			ResponseEntity<Map<String, Object>> response = 
					restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
			
			// Then: a not found (404) status code is returned
			
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
			
			// And: an error message is returned
			Map<String, Object> error = response.getBody();
			assertErrorMessageValid(error, HttpStatus.BAD_REQUEST);
		}
						
	}
	
	static Stream<Arguments> parametersForInvalidInput(){
		// @formatter:off
		return Stream.of(
				arguments("ADIDAS_FINALE_ISTANBUL", "#!$^%^", "Year contains non numeric numbers"),
				arguments("ADIDAS_FINALE_ISTANBUL", "4".repeat(Constants.YEAR_MAX_LENGTH+1), "Year length too long"),
				arguments("INVALID", "2023", "Ball type is not an enum value")
				);
		// @formatter:on
	}
	
	@Nested
	@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
	@ActiveProfiles("test")
	@Sql(scripts = {"classpath:flyway\\migrations\\V1.0__Soccer_Schema.sql",
			"classpath:flyway\\migrations\\V1.1__Soccer_Data.sql"}, 
			config = @SqlConfig(encoding = "utf-8"))
	class TestsThatPolluteTheApplicationContext extends FetchBallTestSupport{
		
		@MockBean
		private BallSalesService ballSalesService;
		
		@Test
		void testsThatAnErrorMessageResultsInA500Status() {
			
			// Given: a valid type, year, and URI
			BallType type = BallType.ADIDAS_FINALE_ISTANBUL;
			String year = "3564";
			String uri = 
					String.format("http://localhost:%d/soccer?type=%s&year=%s", serverPort, type, year);
			
		    doThrow(new RuntimeException("ouch!")).when(ballSalesService).fetchBalls(type, year);
		    
		    // When: a connection is made to the URI
		    
		    ResponseEntity<Map<String, Object>> response = 
		    		getRestTemplate().exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
		    
		    // Then an internal server error(500) status is returned
		    
		    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
		    
		    // And: an error message is returned
		    Map<String, Object> error = response.getBody();
		    assertErrorMessageValid(error, HttpStatus.INTERNAL_SERVER_ERROR);
		    
		}
	}
	
	

}
