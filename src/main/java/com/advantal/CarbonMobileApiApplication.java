package com.advantal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.advantal")
public class CarbonMobileApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarbonMobileApiApplication.class, args);
	}

}
