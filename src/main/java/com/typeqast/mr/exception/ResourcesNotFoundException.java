package com.typeqast.mr.exception;


public class ResourcesNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1942981689124942284L;
	public final String message;

	public ResourcesNotFoundException(String message) {
		this.message=message;
	}
	
	@Override
	public String getMessage(){
        return message;
    }

}
