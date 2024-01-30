package com.mx.zorktec.backForTolucaLaBellaPage.entities;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimpleResponse implements Serializable{

	private static final long serialVersionUID = 1908765456554L;
	
	public static final int OK = 1;
	public static final int ERROR = 0;

	@JsonProperty("s")
	private int statusCode = OK;

	@JsonProperty("m")
	private String message = "";
	
	@JsonProperty("v")
	Map<String, String> validations;

	@JsonProperty("r")
	private Serializable result;

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Serializable getResult() {
		return result;
	}

	public void setResult(Serializable result) {
		this.result = result;
	}
	
	
	public Map<String, String> getValidations() {
		return validations;
	}

	public void setValidations(Map<String, String> validations) {
		this.validations = validations;
	}

	public void setError(String msg) {
		this.setMessage(msg);
		this.statusCode = ERROR;
	}
	
	public void setError(Map<String, String> validations) {
		this.setValidations(validations);
		this.statusCode = ERROR;
	}

}
