package com.multitab.bookingScheduleQuery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BookingScheduleQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingScheduleQueryApplication.class, args);
	}

}
