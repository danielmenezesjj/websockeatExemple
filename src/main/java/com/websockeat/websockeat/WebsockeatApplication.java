package com.websockeat.websockeat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WebsockeatApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebsockeatApplication.class, args);
	}

}
