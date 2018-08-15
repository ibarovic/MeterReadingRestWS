package com.typeqast.mr.controller;



import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.typeqast.mr.exception.RatioDeleteException;
import com.typeqast.mr.exception.ResourcesNotFoundException;
import com.typeqast.mr.exception.RestException;
import com.typeqast.mr.exception.X509AuthentificationException;
import com.typeqast.mr.model.Error;
import com.typeqast.mr.model.RestResponse;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionController {
	
	private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);
	private static final String PATTERN_LOGGER_ERROR="Failed URL: {} Exception: {}";
	
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<RestResponse> HttpRequestMethodNotSupportedExceptionHandler(HttpServletRequest req, Exception e) throws RestException {     
    	logger.error(PATTERN_LOGGER_ERROR, req.getRequestURL(), e);
		RestResponse tResponse = new RestResponse();
		tResponse.setError(new Error(HttpStatus.METHOD_NOT_ALLOWED, e.getLocalizedMessage()));
		return new ResponseEntity<>(tResponse, tResponse.getError().getStatus());
    }
    
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<RestResponse> httpMessageNotReadableExceptionHandler(HttpServletRequest req, Exception e) throws RestException{
    	logger.error(PATTERN_LOGGER_ERROR, req.getRequestURL(), e);
    	RestResponse tResponse = new RestResponse(); 
		tResponse.setError(new Error(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase()));
		return new ResponseEntity<>(tResponse, tResponse.getError().getStatus());
    }
    
//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    public ResponseEntity<Object> handleMethodArgumentNotValid(HttpServletRequest req, MethodArgumentNotValidException e) {
//    	logger.error(PATTERN_LOGGER_ERROR, req.getRequestURL(), e);
//    	RestResponse tResponse = new RestResponse(); 
//    	
//    	List<String> errors = new ArrayList<String>();
//        for (FieldError error : e.getBindingResult().getFieldErrors()) {
//            errors.add(error.getField() + ": " + error.getDefaultMessage());
//        }
//        for (ObjectError error : e.getBindingResult().getGlobalErrors()) {
//            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
//        }
//		tResponse.setError(new Error(HttpStatus.BAD_REQUEST, errors.toString()));
//		return new ResponseEntity<>(tResponse, tResponse.getError().getStatus());
//    }
    
    
    @ExceptionHandler(value = UnsatisfiedServletRequestParameterException.class)
    public ResponseEntity<RestResponse> UnsatisfiedServletRequestParameterExceptionExceptionHandler(HttpServletRequest req, UnsatisfiedServletRequestParameterException e) throws RestException{
    	logger.error(PATTERN_LOGGER_ERROR, req.getRequestURL(), e);
       	RestResponse tResponse = new RestResponse();
		tResponse.setError(new Error(HttpStatus.BAD_REQUEST, e.getMessage()));
		return new ResponseEntity<>(tResponse, tResponse.getError().getStatus());
    }
    
    @ExceptionHandler(value = ResourcesNotFoundException.class)
    public ResponseEntity<RestResponse> resourcesNotFoundExceptionExceptionHandler(HttpServletRequest req, ResourcesNotFoundException e) throws RestException{
    	logger.error(PATTERN_LOGGER_ERROR, req.getRequestURL(), e);
       	RestResponse tResponse = new RestResponse();
		tResponse.setError(new Error(HttpStatus.NOT_FOUND, e.getMessage()));
		return new ResponseEntity<>(tResponse, tResponse.getError().getStatus());
    }
    
    @ExceptionHandler(value = RatioDeleteException.class)
    public ResponseEntity<RestResponse> ratioDeleteExceptionExceptionHandler(HttpServletRequest req, RatioDeleteException e) throws RestException{
    	logger.error(PATTERN_LOGGER_ERROR, req.getRequestURL(), e);
       	RestResponse tResponse = new RestResponse();
		tResponse.setError(new Error(HttpStatus.BAD_REQUEST, e.getMessage()));
		return new ResponseEntity<>(tResponse, tResponse.getError().getStatus());
    }
    
    @ExceptionHandler(value = X509AuthentificationException.class)
    public ResponseEntity<RestResponse> restValidationExceptionHandler(HttpServletRequest req, X509AuthentificationException e) throws RestException{
    	logger.error(PATTERN_LOGGER_ERROR, req.getRequestURL(), e);
       	RestResponse tResponse = new RestResponse();
		tResponse.setError(new Error(HttpStatus.UNAUTHORIZED, e.getMessage()));
		return new ResponseEntity<>(tResponse, tResponse.getError().getStatus());
    }
    
	
    @ExceptionHandler(value = RestException.class)
    public ResponseEntity<RestResponse> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        
    	logger.error(PATTERN_LOGGER_ERROR, req.getRequestURL(), e);
    	
    	// If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;
        
		RestResponse tResponse = new RestResponse();
		tResponse.setError(new Error(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
		return new ResponseEntity<>(tResponse, tResponse.getError().getStatus());
    }
    
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<RestResponse> handleAllExceptions(HttpServletRequest req, Exception e) throws Exception {
        
    	logger.error(PATTERN_LOGGER_ERROR, req.getRequestURL(), e);
    	
    	// If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it - like the OrderNotFoundException example
        // at the start of this post.
        // AnnotationUtils is a Spring Framework utility class.
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;
        
		RestResponse tResponse = new RestResponse();
		tResponse.setError(new Error(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()));
		return new ResponseEntity<>(tResponse, tResponse.getError().getStatus());
    }

}
