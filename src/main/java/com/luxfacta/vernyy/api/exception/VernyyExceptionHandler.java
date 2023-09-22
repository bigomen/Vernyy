package com.luxfacta.vernyy.api.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.luxfacta.vernyy.api.message.ErrorMessage;
import com.luxfacta.vernyy.api.message.IBaseMessage;


@ControllerAdvice
public class VernyyExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({Exception.class})
    public ResponseEntity<IBaseMessage> handleException(Exception exc){
    	
    	if (exc instanceof BusinessSecurityException || exc instanceof BusinessRuleException || exc instanceof NotFoundException) {
    		return new ResponseEntity<>(new ErrorMessage(exc.getMessage()), HttpStatus.BAD_REQUEST);
    	}
    	
		return new ResponseEntity<>(new ErrorMessage("Erro desconhecido. Tente mais tarde."), HttpStatus.INTERNAL_SERVER_ERROR);
    	
    }
    
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
      
    	ex.printStackTrace();
    	return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    	
    	
    }
    
    /*
    protected ResponseEntity<Object> createResponseEntity(
			@Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
    	return null;
    }
    */

}
