package com.mx.zorktec.apiBackAdminTienda.exceptions;

import java.io.Serializable;

public class ProveedorException extends Exception implements Serializable{

	private static final long serialVersionUID = 1696847473848585L;
	
	
	public ProveedorException() {
		
	}
	
	
	public ProveedorException(String msg) {
		super(msg);
	}

}
