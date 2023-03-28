package com.example.demo.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int productId;
	
	private String expdate;
	public String getExpdate() {
		return expdate;
	}
	public void setExpdate(String expdate) {
		this.expdate = expdate;
	}
	private String productName;
	private int productPrice;
	private int productAvailability;
	private String prescription;
	public String getPrescription() {
		return prescription;
	}
	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public int getProductAvailability() {
		return productAvailability;
	}
	public void setProductAvailability(int productAvailability) {
		this.productAvailability = productAvailability;
	}
	public Product(int productId, String productName, int productPrice, int productAvailability,String prescription) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productAvailability = productAvailability;
		this.prescription=prescription;
	}
	public Product() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", expdate=" + expdate + ", productName=" + productName
				+ ", productPrice=" + productPrice + ", productAvailability=" + productAvailability + ", prescription="
				+ prescription + "]";
	}

	

}
