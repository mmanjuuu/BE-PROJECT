package com.example.demo.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Delivery {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int orderid;
	private int customerid;
	private int productId;
	private int TotalPrice;
	//@Id
	private String productName;
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	private int productQuantity;
	private String managerStatus;
	private String deliveryStatus;
	public int getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(int productPrice) {
		this.TotalPrice = productPrice;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	//private int productAvailability;
	
	
	public int getCustomerid() {
		return customerid;
	}
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getManagerStatus() {
		return managerStatus;
	}
	public void setManagerStatus(String managerStatus) {
		this.managerStatus = managerStatus;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}
	public Delivery(int customerid, int productId, String managerStatus, String deliveryStatus) {
		super();
		this.customerid = customerid;
		this.productId = productId;
		this.managerStatus = managerStatus;
		this.deliveryStatus = deliveryStatus;
	}
	public Delivery() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Delivery [customerid=" + customerid + ", productId=" + productId + ", managerStatus=" + managerStatus
				+ ", deliveryStatus=" + deliveryStatus + "]";
	}

	

}
