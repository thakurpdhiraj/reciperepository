/**
 * 
 */
package com.tcs.demo.recipe.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Generalized Error class to represent API error 
 * @author Dhiraj
 *
 */
public class ApiError implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private HttpStatus status;
	
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date timestamp;
	
	private String message;
	
	private List<String> debugMessage;
	
	private ApiError() {
		timestamp = new Date();
	}

	public ApiError(HttpStatus status){
		this();
		this.status = status;
	}
	
	public ApiError(HttpStatus status, List<String> ex) {
	       this();
	       this.status = status;
	       this.message = "Error occured";
	       this.debugMessage = ex;
	   }
	
	public ApiError(HttpStatus status,String message, List<String> ex) {
	       this();
	       this.status = status;
	       this.message = message;
	       this.debugMessage = ex;
	   }

	/**
	 * @return the status
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	/**
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the debugMessage
	 */
	public  List<String>  getDebugMessage() {
		return debugMessage;
	}

	/**
	 * @param debugMessage the debugMessage to set
	 */
	public void setDebugMessage( List<String>  debugMessage) {
		this.debugMessage = debugMessage;
	}
		
}
