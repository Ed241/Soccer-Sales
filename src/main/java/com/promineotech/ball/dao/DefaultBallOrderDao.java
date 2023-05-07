package com.promineotech.ball.dao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.promineotech.ball.entity.Ball;
import com.promineotech.ball.entity.BallSize;
import com.promineotech.ball.entity.BallType;
import com.promineotech.ball.entity.Customer;
import com.promineotech.ball.entity.Order;

@Component
public class DefaultBallOrderDao implements BallOrderDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	
	@Override
	public Order saveOrder(Customer customer, Ball ball, BigDecimal price) {
		SqlParams params = generateInsertSql(customer, ball, price);
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(params.sql, params.source, keyHolder);
		
		Long orderPK = keyHolder.getKey().longValue();
		
		// @formatter:off
		return Order.builder()
				.orderPk(orderPK)
				.customer(customer)
				.ball(ball)
				.build();
		// @formatter:on
		
	}
	
	private void saveBallOrder(List<Ball> balls, Long orderPK) {
		for(Ball ball: balls) {
			SqlParams params = generateInsertSql(ball, orderPK);
			jdbcTemplate.update(params.sql, params.source);
		}
	}
	
	private SqlParams generateInsertSql(Ball ball, Long orderPK) {
		SqlParams params = new SqlParams();
		
		// @formatter:off
		params.sql = ""
			+ "INSERT INTO ball_order ("
			+ "ball_pk, order_pk"
			+ ") VALUES ("
			+ ":ball_pk, :order_pk"
			+ ")";
		// @formatter:on
		
		params.source.addValue("ball_pk", ball.getBallPk());
		params.source.addValue("order_fk", orderPK);
		
		return params;
	}
	
	
	private SqlParams generateInsertSql(Customer customer, Ball ball, BigDecimal price) {
		// @formatter:off
		String sql = ""
			+ "INSERT INTO orders ("
			+ "custimer_fk, ball_fk, price"
			+ ") VALUES ("
			+ ":customer_fk, :ball_fk, :price"
			+ ")";
		// @formatter:on
		
		SqlParams params = new SqlParams();
		
		params.sql = sql;
		params.source.addValue("customer_fk", customer.getCustomerPk());
		params.source.addValue("ball_fk", ball.getBallPk());
		params.source.addValue("price", price);
		
		return params;
	}

	@Override
	public Optional<Customer> fetchCustomer(String customer) {
		// @formatter:off
		String sql = ""
			+ "SELECT * "
			+ "FROM customer "
			+ "WHERE customer_id = :customer_id";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		params.put("customer_id", customer);
		
		return Optional.ofNullable(jdbcTemplate.query(sql, params, new CustomerResultSetExtractor()));
	}
	
	@Override
	public Optional<Ball> fetchBall(BallType type, BallSize size, String year) {
		// @formatter:off
		String sql = ""
			+ "SELECT * "
			+ "FROM ball "
			+ "WHERE ball_id = :ball_id "
			+ "AND ball_size = :ball_size "
			+ "AND ball_year = :ball_year";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		params.put("ball_id", type);
		params.put("ball_size", size);
		params.put("ball_year", year);
		
		
		return Optional.ofNullable(jdbcTemplate.query(sql, params, new BallResultSetExtractor()));
	}

	
	
	class CustomerResultSetExtractor implements ResultSetExtractor<Customer>{

		@Override
		public Customer extractData(ResultSet rs) throws SQLException, DataAccessException {
			rs.next();
			
			// @formatter:off
			return Customer.builder()
				.customerId(rs.getString("customer_id"))
				.customerPk(rs.getLong("customer_pk"))
				.firstName(rs.getString("first_name"))
				.lastName(rs.getString("last_name"))
				.phone(rs.getString("phone"))
				.build();
			// @formatter:on
		}
		
	}
	
	class BallResultSetExtractor implements ResultSetExtractor<Ball>{
		
		@Override
		public Ball extractData(ResultSet rs) throws SQLException, DataAccessException {
			rs.next();
			// @formatter:off
			return Ball.builder()
					.ballId(BallType.valueOf(rs.getString("ball_id")))
					.ballPk(rs.getLong("ball_pk"))
					.ballSize(BallSize.valueOf(rs.getString("ball_size")))
					.year(rs.getString("ball_year"))
					.price(rs.getBigDecimal("price"))
					.build();
			// @formatter:on
			
		}
	}
	
	class SqlParams{
		String sql;
		MapSqlParameterSource source = new MapSqlParameterSource();
	}

	

	

	

	

	
	
}
