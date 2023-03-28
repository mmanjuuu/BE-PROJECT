package com.example.demo.service.impl;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.multipart.MultipartFile;

//import com.example.demo.Entity.FileResponse;
import com.example.demo.Entity.Prescription;
import com.example.demo.Exception.ResourceNotFound;
import com.example.demo.Repository.PrescriptionRep;


public class Prescriptionservice {
	 
	@Autowired
	private PrescriptionRep prerep;
	
	
	public String store(MultipartFile file,int i) throws IOException {
		String fileName = file.getOriginalFilename();
		Prescription f = new Prescription(String.valueOf(i), fileName, file.getContentType(), file.getBytes());
		prerep.save(f);
		
		return  "File Uploaded Succesfully";
	}
	
	public Prescription getFileById(String id) {
		
		Optional<Prescription> fileOptional = prerep.findById(id);
		
		if(fileOptional.isPresent()) {
			return fileOptional.get();
		}
		else
		{
			throw new ResourceNotFound();
		}
	}
	
//	private FileResponse mapToFileResponse(Prescription p) {
//		return new FileResponse(p.getId(), p.getType(), p.getName());
//	}
	

	public Prescriptionservice() {
		// TODO Auto-generated constructor stub
	}

}
