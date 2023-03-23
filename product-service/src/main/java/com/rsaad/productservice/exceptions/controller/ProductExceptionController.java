package com.rsaad.productservice.exceptions.controller;

import com.rsaad.productservice.exceptions.ProductNotFoundException;
import com.rsaad.productservice.exceptions.validation.ErrorResult;
import com.rsaad.productservice.exceptions.validation.FieldValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductExceptionController {
    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<Object> exception(ProductNotFoundException exception){
        System.out.println(exception.getMessage());
        return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResult errorResult = new ErrorResult();
        for(FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorResult.getFieldErrors().add(new FieldValidationError(fieldError.getField(),fieldError.getDefaultMessage()));
        }
        return errorResult;
    }

}
