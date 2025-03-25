package com.schedulo.schedulo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ScheduloApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduloApplication.class, args);
	}

}
