package com.conversor.currency.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
	public List<FieldError> handle(MethodArgumentNotValidException exception) {
		List<FieldError> listFieldError = new ArrayList<>();
		
		List<org.springframework.validation.FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			LocaleContextHolder.getLocale();
			String message = messageSource.getMessage(e, Locale.US);
			FieldError error = new FieldError(e.getField(), message);
			listFieldError.add(error);
		});
		
		return listFieldError;
	}
}
