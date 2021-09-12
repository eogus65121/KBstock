package com.ezTstock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EzTstockApplication {

	public static void main(String[] args) {
		SpringApplication.run(EzTstockApplication.class, args);
	}
}
