package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import com.example.demo.Repository.CustomerRep;
import com.example.demo.Repository.DeliveryRep;
import com.example.demo.Repository.PrescriptionRep;
import com.example.demo.Repository.ProductRep;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Delivery;
import com.example.demo.Entity.Prescription;
import com.example.demo.Entity.Product;
import com.example.demo.Exception.OutofStock;
import com.example.demo.service.DeliveryService;
@Service
public class Deliveryservice implements DeliveryService{
	
	@Autowired
	DeliveryRep deliveryrep;
	
	@Autowired
	ProductRep productrep;
	
	@Autowired
	CustomerRep customerrep;
	
	@Autowired
	PrescriptionRep prep;
	
	@Autowired
	JavaMailSender javamailsender;

	public Deliveryservice() {
		// TODO Auto-generated constructor stub
		
	}
	public String Managerstatus(int orderid)
	{
		Optional <Delivery> d=deliveryrep.findAll().stream().filter(x->x.getOrderid()==orderid).findAny();
		if(d.isPresent())
		{
			
			//System.out.println(CustomerId);
			Delivery de=new Delivery();
			de=d.get();
			if(de.getManagerStatus()=="PENDING")
			{
			Optional <Customer> c=customerrep.findById(de.getCustomerid());
			Customer c1=c.get();
			de.setManagerStatus("APPROVED");
			de.setDeliveryStatus("DISPATCHED");
			deliveryrep.save(de);
			
			SimpleMailMessage m = new SimpleMailMessage();
			m.setTo(c1.getEmail());
			m.setSubject("NEW ORDER");
			m.setText("Hi "+c1.getCustomerName()+" !!"+'\n'+"Thank you for placing your order with us. We will ship your order in the next 24-48 hours."+'\n'+'\n'+  "Order details: "+'\n'+"Product Name: "+ de.getProductName()+'\n'+"Quantity:  "+de.getProductQuantity()+'\n'+"The total cost is "+de.getTotalPrice()+'\n'+"Kindly pay cash on delivery or using UPI services for the product to the delivery partner"+'\n'+"Shipping Address: "+c1.getCustomerAddress()+'\n'+'\n'+"Once the order is placed it cannot be cancelled,however you can return the package during delivery at the doorstep.For queries email to abcdeliveryteam@gmail.com");
			javamailsender.send(m);
			//return "Your order has been placed! Your Order Id is: "+orderid;
			return "Approved";
			}
			else
			{
				return "Already approved";
			}
		}
		else
		{
			return "Record not found";
		}
		
	}
	
	public String ManagerstatusRejected(int orderid)
	{
		Optional <Delivery> d=deliveryrep.findAll().stream().filter(x->x.getOrderid()==orderid).findAny();
		if(d.isPresent())
		{
			
			//System.out.println(CustomerId);
			Delivery de=new Delivery();
			de=d.get();
			if(de.getManagerStatus()=="PENDING")
			{
			Optional <Customer> c=customerrep.findById(de.getCustomerid());
			Customer c1=c.get();
			de.setManagerStatus("REJECTED");
			de.setDeliveryStatus("NA");
			deliveryrep.save(de);
			
//			SimpleMailMessage m = new SimpleMailMessage();
//			m.setTo(c1.getEmail());
//			m.setSubject("NEW ORDER");
//			m.setText("Hi "+c1.getCustomerName()+" !!"+'\n'+"Thank you for placing your order with us. We will ship your order in the next 24-48 hours."+'\n'+'\n'+  "Order details: "+'\n'+"Product Name: "+ de.getProductName()+'\n'+"Quantity:  "+de.getProductQuantity()+'\n'+"The total cost is "+de.getTotalPrice()+'\n'+"Kindly pay cash on delivery or using UPI services for the product to the delivery partner"+'\n'+"Shipping Address: "+c1.getCustomerAddress()+'\n'+'\n'+"Once the order is placed it cannot be cancelled,however you can return the package during delivery at the doorstep.For queries email to abcdeliveryteam@gmail.com");
//			javamailsender.send(m);
//			//return "Your order has been placed! Your Order Id is: "+orderid;
			return "REJECTED";
			}
			else
			{
				return "Already rejected";
			}
		}
		else
		{
			return "Record not found";
		}
		
	}
	
	public String UpdateStatus(String ProductName, int ProductQuantity,int CustomerId)
	{
		
		Optional <Product> p=productrep.findAll().stream().filter(x->x.getProductName().toUpperCase().equals(ProductName.toUpperCase())).findAny();
		
		Optional <Customer> c=customerrep.findById(CustomerId);
		//Optional <Delivery> d=deliveryrep.findAll().stream().filter(x->x.getProductId()==(Productid)).findAny();
		if(c.isPresent()) {
		if(p.isPresent())
		{
			Product p1=p.get();
			Customer c1=c.get();
			if(ProductQuantity<=p1.getProductAvailability())
			{
			Delivery d=new Delivery();
			d.setProductId(p1.getProductId());
			d.setProductName(ProductName.toUpperCase());
			d.setCustomerid(CustomerId);
			d.setProductQuantity(ProductQuantity);
			if(p1.getPrescription().toUpperCase().equals("NEEDED"))
			{
				d.setManagerStatus("PENDING");
				d.setDeliveryStatus("WAITING FOR APPROVAL");
			}
			else
			{
				d.setManagerStatus("APPROVED");
				d.setDeliveryStatus("DISPATCHED");
			}
			
//			d.setDeliveryStatus("DISPATCHED");
			d.setTotalPrice(0);
//			p1.setProductAvailability(p1.getProductAvailability()-ProductQuantity);
//			if(p1.getProductAvailability()==0)
//			{
//				SimpleMailMessage n = new SimpleMailMessage();
//				n.setTo("abcmanager0@gmail.com");
//				n.setSubject("PRODUCT OUT OF STOCK");
//				n.setText(p1.getProductName()+" is out of stock.Kindly restock the medicine and update the database");
//				javamailsender.send(n);
//			}
			d.setTotalPrice(p1.getProductPrice()*d.getProductQuantity());
			deliveryrep.save(d);
			productrep.save(p1);
			 int s=d.getOrderid();
			 if(d.getManagerStatus().equals("APPROVED"))
			 {
			 SimpleMailMessage m = new SimpleMailMessage();
				m.setTo(c1.getEmail());
				m.setSubject("NEW ORDER");
				m.setText("Hi "+c1.getCustomerName()+" !!"+'\n'+"Thank you for placing your order with us. We will ship your order in the next 1-2 hours."+'\n'+'\n'+  "Order details: "+'\n'+"Product Name: "+ d.getProductName()+'\n'+"Quantity:  "+d.getProductQuantity()+'\n'+"The total cost is "+d.getTotalPrice()+'\n'+"Kindly pay cash on delivery or using UPI services for the product to the delivery partner"+'\n'+"Shipping Address: "+c1.getCustomerAddress()+'\n'+'\n'+"Once the order is placed it cannot be cancelled,however you can return the package during delivery at the doorstep.For queries email to abcdeliveryteam@gmail.com");
				javamailsender.send(m);
				return "Your order has been placed! Your Order Id is: "+s;
			 }
			 else
			 {
				 return "Your order id is: "+s; 
			 }
//			return "Your order has been placed! Your Order Id is: "+s;
			}
			else
			{ 
				throw new OutofStock();
			}
			
			//return "Delivered";
		}
		else
		{
			return "Product not found";
		}}
		else
			return "User not Found";
		
	}
	public String delete(int orderid)
	{	
		Optional <Delivery> d=deliveryrep.findAll().stream().filter(x->x.getOrderid()==orderid).findAny();
		if(d.isPresent())
		{Delivery du=d.get();
		
//		Customer c=new Customer();
//		c.setCustomerid(id);
//		c.setCustomerAddress(address);
//		c.setCustomerName(name);
//		c.setEmail(email);
//		c.setPassword(password);
		if(du.getManagerStatus().equals("PENDING"))
		{
			Optional <Customer> c=customerrep.findById(du.getCustomerid());
//			Optional <Prescription> p=prep.findAll().stream().filter(x->x.getId().equals(du.getOrderid())).findAny();
//			Prescription p1=p.get();
//			prep.delete(p1);
			Customer c1=c.get();
			SimpleMailMessage m = new SimpleMailMessage();
			m.setTo(c1.getEmail());
			m.setSubject("ORDER HAS BEEN CANCELLED!!");
			m.setText("Hi "+c1.getCustomerName()+" !!"+'\n'+"The prescription you uploaded for the order id: "+du.getOrderid()+" is not valid. Kindly order again and upload the valid prescription as well.");
			javamailsender.send(m);
			
		}
		else if(du.getManagerStatus().equals("APPROVED")&&du.getDeliveryStatus().equals("DISPATCHED"))
		{
			Optional <Customer> c=customerrep.findById(du.getCustomerid());
			Customer c1=c.get();
//			Optional <Prescription> p=prep.findAll().stream().filter(x->x.getId().equals(du.getOrderid())).findAny();
//			Prescription p1=p.get();
//			prep.delete(p1);
			SimpleMailMessage m = new SimpleMailMessage();
			m.setTo(c1.getEmail());
			m.setSubject("ORDER HAS BEEN CANCELLED!!");
			m.setText("Hi "+c1.getCustomerName()+" !!"+'\n'+"Since you returned the products during delivery at the door step, your order has been cancelled");
			javamailsender.send(m);
		}
//		else if(du.getManagerStatus().equals("APPROVED") && du.getManagerStatus().equals("DELIVERED"))
//		{
//			return 
//		}
		
		deliveryrep.delete(du);
		int i=du.getOrderid();
		String s=Integer.toString(i);
		Optional <Prescription> p=prep.findAll().stream().filter(x->x.getId().equals(s)).findAny();
		if(p.isPresent())
		{
		Prescription p1=p.get();
		prep.delete(p1);
		}
		return "Record deleted";
		}
		else
		{
			return "No record found";
		}
	}
	
	public String DeliveryStatus(int orderId,String ProductName)
	{
		Optional <Delivery> d=deliveryrep.findAll().stream().filter(x->x.getOrderid()==orderId).findAny();
		if(d.isPresent())
		{
			//System.out.println(CustomerId);
			Delivery de=d.get();
			if(de.getProductName().equals(ProductName.toUpperCase())&&de.getManagerStatus().equals("APPROVED"))
			{
				de.setDeliveryStatus("DELIVERED");
				Optional <Product> p=productrep.findAll().stream().filter(x->x.getProductName().equals(ProductName.toUpperCase())).findAny();
				Optional <Customer> c=customerrep.findAll().stream().filter(x->x.getCustomerid()==de.getCustomerid()).findAny();
				Product pr=p.get();
				pr.setProductAvailability(pr.getProductAvailability()-de.getProductQuantity());
				if(pr.getProductAvailability()<=1)
				{
					SimpleMailMessage n = new SimpleMailMessage();
					n.setTo("abcmanager0@gmail.com");
					n.setSubject("PRODUCT OUT OF STOCK");
					n.setText(pr.getProductName()+" is at its minimum availability.Kindly restock the medicine and update the database");
					javamailsender.send(n);
				}
				Customer c1=c.get();
				//de.setTotalPrice(pr.getProductPrice()*de.getProductQuantity());
				deliveryrep.save(de);
				 SimpleMailMessage m = new SimpleMailMessage();
					m.setTo(c1.getEmail());
					m.setSubject("YOUR ORDER HAS BEEN DELIVERED!");
					m.setText("Hi "+c1.getCustomerName()+" !!"+'\n'+"Your order has been delivered"+'\n'+'\n'+ "Order details: "+'\n'+ "Product Name: "+de.getProductName()+'\n'+"Quantity:  "+de.getProductQuantity()+'\n'+"Price: "+de.getTotalPrice()+'\n'+ "For queries email to abcdeliveryteam@gmail.com");
					javamailsender.send(m);
					
					m.setTo("abcmanager0@gmail.com");
					m.setSubject("PRODUCT DELIVERY");
					m.setText("The following order has been delivered "+'\n'+'\n'+  "Order details: "+'\n'+ "Customer Name: "+ c1.getCustomerName()+'\n'+"Order Id: "+de.getOrderid()+'\n'+"Product Name: "+ de.getProductName()+'\n'+"Quantity:  "+de.getProductQuantity()+'\n'+"Price: "+de.getTotalPrice());
					javamailsender.send(m);
					
				return "Delivered";
			}
			else
			{
				return "Order doesn't exist!";
			}
			
				
		}
		else
		{
			return "Order doesn't exist!";
		}
		
	}
	
	public void deleteFromtable()
	{
		List<Delivery> d=deliveryrep.findByManagerStatus("REJECTED");
		System.out.println(d);
		if(!d.isEmpty())
		{
			for(Delivery value:d)
			{
				deliveryrep.delete(value);
			}
		}
		else
		{
			System.out.println("NO COLUMN FOUND AS REJECTED");
		}
}

}

