package com.digitalbooks.book;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookApplication implements CommandLineRunner {
	
	@Override
	public void run(String... args) {
		
		System.out.println("Application is Running...");

	}

	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}

}
