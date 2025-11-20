package com.yschoi.demoapp.common.error.handler;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import com.yschoi.demoapp.common.util.ResponseUtil;

@RestControllerAdvice
public class GlobalExceptionHandler {	
	// HttpClientErrorException
	@ExceptionHandler(HttpClientErrorException.class)
	public Map<String, Object> exceptionHandler(HttpClientErrorException e) {
		return ResponseUtil.getResultMap(e.getStatusCode(), e.getMessage());
	}
	
	// MethodNotAllowed
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Map<String, Object> exceptionHandler(HttpRequestMethodNotSupportedException e) {
		return ResponseUtil.getResultMap(HttpStatus.METHOD_NOT_ALLOWED);
	}

	// InternalServerError
	@ExceptionHandler(Exception.class)
	public Map<String, Object> exceptionHandler(Exception e) {
		e.printStackTrace();
		return ResponseUtil.getResultMap(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}
}
