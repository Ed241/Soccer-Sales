package com.promineotech.ball;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.promineotech.ComponentScanMarker;

@SpringBootApplication(scanBasePackageClasses = { ComponentScanMarker.class} )
public class SoccerBallSales {

	
	public static void main(String[] args) {
		SpringApplication.run(SoccerBallSales.class, args);
	}
}
