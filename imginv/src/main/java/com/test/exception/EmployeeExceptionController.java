package com.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionController {
	@ExceptionHandler(value = EmployeeExceptionValidation.class)
	public ResponseEntity<Object> exception(EmployeeExceptionValidation exception) {
		return new ResponseEntity<>("Geting Error while gathering Employee Details ", HttpStatus.NOT_FOUND);
	}
}
