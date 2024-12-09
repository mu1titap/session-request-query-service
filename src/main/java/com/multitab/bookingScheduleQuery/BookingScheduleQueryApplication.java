package com.multitab.bookingScheduleQuery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BookingScheduleQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookingScheduleQueryApplication.class, args);
	}

}
