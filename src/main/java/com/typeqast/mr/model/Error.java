package com.typeqast.mr.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Error implements Serializable{
	
	private static final long serialVersionUID = 7492601921506481123L;
	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private List<String> details;

	private Error() {
		timestamp = LocalDateTime.now();
	}
	
	public Error(HttpStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
	}
	
	public Error(HttpStatus status, String message, List<String> details) {
		this();
		this.status = status;
		this.message = message;
		this.details = details;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}
}
