package com.mx.zorktec.apiBackAdminTienda.utilities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespuestasRest  implements Serializable  {

	private static final long serialVersionUID = 1L;
	public static final int OK = 1;
	public static final int ERROR = 0;
	
	@JsonProperty("s")
	private int statusCode = OK;

	@JsonProperty("m")
	private String message = "";

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

	public Object getResult() {
		return result;
	}

	public void setResult(Serializable result) {
		this.result = result;
	}
	
	public void setError(String msg) {
		this.setMessage(msg);
		this.statusCode = ERROR;
	}

}
