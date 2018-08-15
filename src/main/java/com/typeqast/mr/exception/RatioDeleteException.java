package com.typeqast.mr.exception;


public class RatioDeleteException extends RuntimeException {

	private static final long serialVersionUID = -78106808070904571L;
	public final String message;

	public RatioDeleteException(String message) {
		this.message=message;
	}
	
	@Override
	public String getMessage(){
        return message;
    }

}
