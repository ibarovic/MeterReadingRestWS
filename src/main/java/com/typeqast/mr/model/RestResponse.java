package com.typeqast.mr.model;

import java.io.Serializable;
import java.util.Map;


public class RestResponse implements Serializable{
	
	private static final long serialVersionUID = -8877376710305998243L;
	private Map<String, Object> data;
	private Error error;
	
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}

}

