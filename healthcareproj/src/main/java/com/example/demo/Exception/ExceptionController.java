package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {
   @ExceptionHandler(value = ResourceNotFound.class)
   public ResponseEntity<Object> exception(ResourceNotFound exception) {
      return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
      
      
   }
   @ExceptionHandler(value = OutofStock.class)
   public ResponseEntity<Object> exception(OutofStock exception) {
      return new ResponseEntity<>("Product Out of Stock", HttpStatus.NOT_FOUND);
}
}