package com.example.demo.Repository;

import java.time.LocalDate;
import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Product;
//import com.example.demo.entity.TimeSheetDetails;

@Repository
public interface ProductRep extends JpaRepository<Product,Integer>{
	
	
//	@Query("SELECT p FROM Product p WHERE p.expdate = :Date")
//	List <Product> findExpdate(LocalDate Date);
//	@Query("SELECT p FROM Product p WHERE CONVERT(DATE, p.expdate) >= DATEADD(day, -10, GETDATE())")
	//@Query("SELECT p FROM Product p ")
//	List<Product> findByExpdateAfter(LocalDate date); 
//	default List<Product> findOlderThan10Days() 
//	{ return findByExpdateAfter(LocalDate.now().plusDays(10)); }
//	//Product checkexpiry();
	
//	@Query("SELECT p FROM Product p WHERE p.productAvailability<=5")
//	List <Product> quantitycheck();
	
	@Query(value = "FROM Product p WHERE p.productAvailability<=5")
	List<Product> quantitycheck();

}
  