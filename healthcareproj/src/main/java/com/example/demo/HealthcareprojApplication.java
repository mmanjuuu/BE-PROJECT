package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.ProductRep;
import com.example.demo.service.ProductService;
import com.example.demo.service.impl.Prescriptionservice;
import com.example.demo.service.impl.Productservice;

@SpringBootApplication
public class HealthcareprojApplication {
	@Bean
	public Prescriptionservice prescriptionservice()
	{
		return new Prescriptionservice();
	}
	
	

	public static void main(String[] args) {
		SpringApplication.run(HealthcareprojApplication.class, args);
	}

}
