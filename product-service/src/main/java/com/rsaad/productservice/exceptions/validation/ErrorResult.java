package com.rsaad.productservice.exceptions.validation;

import java.util.ArrayList;
import java.util.List;

public class ErrorResult {
	private final List<FieldValidationError> fieldErrors = new ArrayList<>();
	
	public ErrorResult() {}
	
	public ErrorResult(String name,String message)
	{
		fieldErrors.add(new FieldValidationError(name,message));
	}
	
	public List<FieldValidationError> getFieldErrors() {
		return fieldErrors;
	}

}
