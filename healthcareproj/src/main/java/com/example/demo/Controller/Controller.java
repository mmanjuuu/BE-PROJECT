package com.example.demo.Controller;

//import java.awt.Image;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.Customer;
import com.example.demo.Entity.Delivery;
//import com.example.demo.Entity.FileResponse;
import com.example.demo.Entity.Prescription;
//import com.example.demo.Entity.Prescription;
import com.example.demo.Entity.Product;

//import com.example.demo.model.FileResponse;
//import com.example.demo.model.Filedb;
//import com.example.demo.Repository.PrescriptionRep;
import com.example.demo.service.impl.Customerservice;
import com.example.demo.service.impl.Deliveryservice;
import com.example.demo.service.impl.Prescriptionservice;
//import com.example.demo.service.impl.Prescriptionservice;
import com.example.demo.service.impl.Productservice;

@RestController
//@RequestMapping("file")
public class Controller {
	
	@Autowired
	Customerservice cService;
	
	@Autowired
	Productservice pService;
	
	@Autowired
	Deliveryservice dService;
	
	@Autowired
	Prescriptionservice ppservice;
	

	
		@GetMapping("/authenticate")
		@ResponseBody
		public String auth(@RequestBody Customer c)
		{
			return cService.Auth(c.getCustomerName(),c.getPassword());
		}
		
//		
		@PostMapping("/deliverystatus/{orderid}/{productName}")
		@ResponseBody
		public String deliverystatus(@PathVariable Map<Integer, String> pathVarsMap)
		{
			int id = Integer.parseInt(pathVarsMap.get("orderid"));
			//System.out.println(id);
			String name = pathVarsMap.get("productName");
			return dService.DeliveryStatus(id,name);
		}
		@GetMapping("/getproductdetails/{productName}")
		@ResponseBody
		public Optional<Product> productDets(@PathVariable String productName)
		{
			
			return pService.ProductDets(productName); 
		}
		
		@PostMapping("/ordering")
		@ResponseBody
		public String updatestatus(@RequestBody Delivery d)
		{
			return dService.UpdateStatus(d.getProductName(),d.getProductQuantity(),d.getCustomerid());
		}
	
		
		
		@PostMapping("/create")
		@ResponseBody
		public String create(@RequestBody Customer c)
		{
			return cService.Create(c.getCustomerid(),c.getCustomerAddress(),c.getCustomerName(),c.getEmail(),c.getPassword());
		}
		@PostMapping("/createproduct")
	    @ResponseBody
	    public String createProduct(@RequestBody Product p)
	    {
	        return pService.createproduct(p.getProductId(),p.getProductName(),p.getProductAvailability(),p.getProductPrice(),p.getExpdate(),p.getPrescription());
	    }
		
		@DeleteMapping("/deleteuser/{customerid}")
		@ResponseBody
		public String delete(@PathVariable int customerid)
		{
			return cService.Delete(customerid);
		}
		
		@PostMapping("/upload")
		//@ResponseBody
		public String uploadFile(@RequestParam("file") MultipartFile  file,int id) throws IOException {
			return ppservice.store(file,id);
		}
		@GetMapping("/{id}")
		public Prescription getFile(@PathVariable String id) {
			
			return ppservice.getFileById(id);
			
		}
		@GetMapping("/downloadFile/{fileId}")
	    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileId) {
	        // Load file from database
	        Prescription dbFile = ppservice.getFileById(fileId);
	        
	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(dbFile.getType()))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getName() + "\"")
	                .body(new ByteArrayResource(dbFile.getData()));
	    }
		
		@PostMapping("/managerstatus/{orderid}")
		@ResponseBody
		public String updatemstatus(@PathVariable int orderid)
		{
			return dService.Managerstatus(orderid);
		}
		
		@PostMapping("/managerstatusrejected/{orderid}")
		@ResponseBody
		public String updatemstatusrejected(@PathVariable int orderid)
		{
			return dService.ManagerstatusRejected(orderid);
		}
		
		@DeleteMapping("/deleteorder/{orderid}")
		@ResponseBody
		public String deleteorder(@PathVariable int orderid)
		{
			return dService.delete(orderid);
		}
//	    @PostMapping("/upload/image")
//	    public ResponseEntity<ImageUploadResponse> uplaodImage(@RequestParam("image") MultipartFile file)
//	            throws IOException {
//
//	        prep.save(
//	                .name(file.getOriginalFilename())
//	                .type(file.getContentType())
//	                .imagedata(Prescriptionservice.compressImage(file.getBytes())).build());
//	        return ResponseEntity.status(HttpStatus.OK)
//	                .body(new ImageUploadResponse("Image uploaded successfully: " +
//	                        file.getOriginalFilename()));
//	    }
		
//		@PostMapping("/checkexpiry")
//		public void expiry()
//		{
//			pService.checkexpirystatus();
//		}
		
		@GetMapping("/checkquantity")
		public String oos()
		{
			pService.checkquan();
			return "quantity verified "+LocalDate.now();
			
		}
		@GetMapping("/hello")
		public String hello()
		{
			System.out.println("Hello");
			return "hi";
		}
		@GetMapping("/delete")
		public String deleterecord()
		{
			dService.deleteFromtable();
			return "deleted";
		}
		
		
}
