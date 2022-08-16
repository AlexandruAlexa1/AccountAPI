package com.aa.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler
	@ResponseBody
	public ErrorResponse handleException(NotFoundException e) {
		ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		
		return error;
	}
	
	@ExceptionHandler
	@ResponseBody
	public ErrorResponse handleException(Exception e) {
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		
		return error;
	}
}
