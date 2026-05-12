package com.severus.books.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BookExceptionHandler {
      @ExceptionHandler
      public ResponseEntity<CustomErrorResponse> handleException (BookNotFoundException exc){
            CustomErrorResponse error = new CustomErrorResponse(
                  HttpStatus.NOT_FOUND.value(),
                  exc.getMessage(),
                  System.currentTimeMillis()
            );
            return new ResponseEntity<>(error , HttpStatus.NOT_FOUND);
      }

      @ExceptionHandler
      public ResponseEntity<CustomErrorResponse> handleException (Exception exc){
            CustomErrorResponse error = new CustomErrorResponse(
                  HttpStatus.BAD_REQUEST.value(),
                  "Invalid Request",
                  System.currentTimeMillis()
            );
            return new ResponseEntity<>(error , HttpStatus.BAD_REQUEST);
      }
}
