package com.example.demo.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int customerid;
	private String customerName;
	private String customerAddress;
	private String email;
	private String password;

	public Customer(int customerid, String customerName, String customerAddress, String email, String password) {
		super();
		this.customerid = customerid;
		this.customerName = customerName;
		this.customerAddress = customerAddress;
		this.email = email;
		this.password = password;
	}

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [customerid=" + customerid + ", customerName=" + customerName + ", customerAddress="
				+ customerAddress + ", email=" + email + ", password=" + password + "]";
	}

	

}
