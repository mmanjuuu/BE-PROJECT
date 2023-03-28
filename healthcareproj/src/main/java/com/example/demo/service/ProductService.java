package com.example.demo.service;

import java.util.Optional;

import com.example.demo.Entity.Product;

public interface ProductService {
	
	Optional<Product> ProdDets(String p);

}
