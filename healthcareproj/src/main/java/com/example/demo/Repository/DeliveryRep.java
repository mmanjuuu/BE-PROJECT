package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Delivery;

@Repository
public interface DeliveryRep extends JpaRepository<Delivery,Integer> {
	
	
	List<Delivery> findByManagerStatus(String managerStatus);

	

}
