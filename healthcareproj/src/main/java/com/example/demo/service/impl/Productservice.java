package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Product;
import com.example.demo.Exception.OutofStock;
import com.example.demo.Exception.ResourceNotFound;
import com.example.demo.Repository.CustomerRep;
import com.example.demo.Repository.ProductRep;
import com.example.demo.service.ProductService;

@Service
public class Productservice implements ProductService{
	
	@Autowired
	ProductRep productrep;
	
//	@Autowired
//	Product product;
	
	@Autowired
	JavaMailSender javamailsender;
	
	public String createproduct(int pid,String pname,int availablility,int price,String date,String prescription)
    {
        Product p=new Product();
        p.setProductId(pid);
        p.setProductName(pname);
        p.setProductAvailability(availablility);
        p.setProductPrice(price);
        p.setExpdate(date);
        p.setPrescription(prescription);
        productrep.save(p);
        return "Product Successfully added";
    }
	
//	public void checkexpirystatus()
//	{
//		List <Product> p=productrep.findExpdate(LocalDate.now());
//		System.out.println(p);
//	}

	public Optional<Product> ProductDets(String ProductName)
	{
		
		int id;
		Optional <Product> p=productrep.findAll().stream().filter(x->x.getProductName().toUpperCase().equals(ProductName.toUpperCase())).findAny();
		System.out.println(p.toString());
		if(p.isPresent())
		{
			Product pr=p.get();
			
			  String s=pr.getExpdate();
			//String s=null;
			  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			  
			  LocalDate date = LocalDate.parse(s, formatter);
			  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		      LocalDate localDate = LocalDate.now();
//			  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		      LocalDate localDate = LocalDate.now();
//		      String date=dtf.format(localDate); 
		      long daysDiff = ChronoUnit.DAYS.between(localDate, date);
		      if(daysDiff<=10)
		      {
		    	  productrep.delete(pr);
		    	  SimpleMailMessage m = new SimpleMailMessage();
					m.setTo("abcmanager0@gmail.com");
					m.setSubject("PRODUCT REMOVED");
					m.setText("Since the expiration date of "+pr.getProductName()+" is in less than 10 days, the product has been removed from the database. Kindly restock the medicine and update the database");
					javamailsender.send(m);
		    	  throw new ResourceNotFound();
		      }
		      else 
		      {
		    	  if(pr.getProductAvailability()>1)
		    	  {
		    	  return productrep.findById(pr.getProductId());	
		    	  }
		    	  else
		    		  throw new OutofStock();
		      }
		    
//			
		}
		else
		{
			throw new ResourceNotFound();
			
		}
		//return Optional.ofNullable(productrep.findById(id).orElseThrow(() -> new ResourceNotFound()));
		
		
	}



	@Override
	public Optional<Product> ProdDets(String p) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public void checkquan() {
		// TODO Auto-generated method stub
		
		List<Product> p=productrep.quantitycheck();
		System.out.println(p.toString());
		if(!p.isEmpty())
		{
			List<String> s=new ArrayList<>();
			for(Product p1:p)
			{
				
				s.add(p1.getProductName());
				
			}
			SimpleMailMessage m = new SimpleMailMessage();
			m.setTo("userhealthcareapp@gmail.com");
			m.setSubject("PRODUCT RESTOCK");
			m.setText("Kindly restock the medicine and update the database "+s.toString());
			javamailsender.send(m);
		}
		
		
		
	}

}
