package com.ck.trade.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ck.trade.exception.TradeNotFoundException;
import com.ck.trade.exception.TradeValidationException;

/**
 * Exception Handler.
 * @author ckarale
 *
 */
@ControllerAdvice
@RequestMapping(produces = "application/json")
public class TradeManagementControllerAdvice extends ResponseEntityExceptionHandler {
	@ExceptionHandler(TradeValidationException.class)
	public ResponseEntity<String> validationFailed(final TradeValidationException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(TradeNotFoundException.class)
	public ResponseEntity<String> notFoundException(final TradeNotFoundException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> allException(final Exception e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}