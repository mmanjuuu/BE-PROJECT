package com.example.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Customer;
import com.example.demo.Exception.ResourceNotFound;
import com.example.demo.Repository.CustomerRep;
import com.example.demo.service.CustomerService;

@Service
public class Customerservice implements CustomerService{

	@Autowired
	CustomerRep customerrep;
	
	@Autowired
	JavaMailSender javamailsender;
	
//	@Autowired
//	Customer customer;
	public Customerservice() {
		// TODO Auto-generated constructor stub
	}
	
	public String Create(int id,String address,String name,String email,String password)
	{
		Customer c=new Customer();
		c.setCustomerid(id);
		c.setCustomerAddress(address);
		c.setCustomerName(name);
		c.setEmail(email);
		c.setPassword(password);
		customerrep.save(c);
		SimpleMailMessage m = new SimpleMailMessage();
		m.setTo(c.getEmail());
		m.setSubject("ACCOUNT CREATED");
		m.setText("Your account in ABC Pharmacy has been created successfully. Welcome!"+'\n'+"Check out our website for all kinds of medicinal products. We deliver in and around the city without any delivery charges involved. We care for your wellbeing and we're the pharmacy you trust."+'\n'+ "For queries email to abcdeliveryteam@gmail.com");
		javamailsender.send(m);
		return "Login created";
	}

	
	public String Auth(String CustomerName, String password)
	{
		Optional <Customer> c=customerrep.findAll().stream().filter(x->x.getCustomerName().equals(CustomerName)).findAny();
		if(c.isPresent())
		{Customer cu=c.get();
			if(cu.getPassword().equals(password))
			{
				String s="Hi"+" "+CustomerName+"!"+ '\n' +"Welcome to ABC Pharmacy";
				return s;
			}
			else 
			{
				return "Invalid Password";
			}	
		}
		else
		{
			return "Invalid.";
		}
			
	}
	public String Delete(int id)
	{	
		Optional <Customer> c=customerrep.findAll().stream().filter(x->x.getCustomerid()==id).findAny();
		if(c.isPresent())
		{Customer cu=c.get();
		
//		Customer c=new Customer();
//		c.setCustomerid(id);
//		c.setCustomerAddress(address);
//		c.setCustomerName(name);
//		c.setEmail(email);
//		c.setPassword(password);
		String e=cu.getEmail();
		SimpleMailMessage m = new SimpleMailMessage();
		m.setTo(e);
		m.setSubject("ACCOUNT DELETED");
		m.setText("Your Account in ABC Pharmacy has been deleted."+'\n'+"We'll miss you! If you wish to continue purchasing any medicinal products check out our website!! We deliver in and around the city without any delivery charges involved. We care for your wellbeing and we're the pharmacy you trust."+'\n'+ "For queries email to abcdeliveryteam@gmail.com");
		javamailsender.send(m);
		customerrep.delete(cu);
		return "Record deleted";
		}
		else
		{
			return "No record found";
		}
	}
	
}
