package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Prescription;

@Repository
public interface PrescriptionRep extends JpaRepository<Prescription,String>{

}
