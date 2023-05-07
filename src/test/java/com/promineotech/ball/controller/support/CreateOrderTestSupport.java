package com.promineotech.ball.controller.support;

public class CreateOrderTestSupport extends BaseTest{
	
	
	protected String createOrderBody() {
		// @formatter:off
		return "{\n"
	    + "  \"customer\":\"SMITH_JOHN\",\n"
	    + "  \"type\":\"BRAZUCA\",\n"
	    + "  \"size\":\"SIZE5\",\n"
	    + "  \"year\":\"2014\"\n"
	    + "}\n"
	    + "";
		// @formatter:on
	}
}
