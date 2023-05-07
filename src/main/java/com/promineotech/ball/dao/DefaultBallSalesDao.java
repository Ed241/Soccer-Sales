package com.promineotech.ball.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.promineotech.ball.entity.Ball;
import com.promineotech.ball.entity.BallSize;
import com.promineotech.ball.entity.BallType;

import lombok.extern.slf4j.Slf4j;

@Service
@Component
@Slf4j
public class DefaultBallSalesDao implements BallSalesDao {
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public List<Ball> fetchBalls(BallType type, String year) {
		log.debug("DAO: type={}, year={}", type, year);
		
		// @formatter:off
		String sql = ""
			+ "SELECT * "
			+ "FROM ball "
			+ "WHERE ball_id = :ball_id AND ball_year = :ball_year";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		params.put("ball_id", type.toString());
		params.put("ball_year", year);
		
		return jdbcTemplate.query(sql, params, new RowMapper<>() {

			@Override
			public Ball mapRow(ResultSet rs, int rowNum) throws SQLException {
				// @formatter:off
				return Ball.builder()
						.ballPk(rs.getLong("ball_pk"))
						.ballId(BallType.valueOf(rs.getString("ball_id")))
						.ballSize(BallSize.valueOf(rs.getString("ball_size")))
						.year(rs.getString("ball_year"))
						.price(rs.getBigDecimal("price"))
						.build();
				// @formatter:on
			}
		});
				
	}

}
