package com.typeqast.mr.exception;

import org.springframework.http.HttpStatus;

public class X509AuthentificationException extends RuntimeException {

	private static final long serialVersionUID = 2002556505326867356L;
	public final String message;
	public final HttpStatus httpStatus;

	public X509AuthentificationException(String message,HttpStatus httpStatus) {
		this.message=message;
		this.httpStatus=httpStatus;
	}
	
	@Override
	public String getMessage(){
        return message;
    }

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	
}