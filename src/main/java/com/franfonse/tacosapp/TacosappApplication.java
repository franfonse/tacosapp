package com.franfonse.tacosapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class TacosappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TacosappApplication.class, args);
	}

}
