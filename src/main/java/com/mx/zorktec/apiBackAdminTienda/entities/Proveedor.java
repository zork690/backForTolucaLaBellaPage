package com.mx.zorktec.apiBackAdminTienda.entities;

import java.io.Serializable;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;


//@Entity
//@Table(name = "proveedores")
public class Proveedor implements IGenericEntity, Serializable{

	private static final long serialVersionUID = 149498585857575L;
	
	
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "id_proveedor")
	//private Long id;
	
	//@Id
	//@Column(name = "correo")
	private String correo;
	
	//@Column(name = "nombre")
	private String nombre;
	
	//@Column(name = "celular")
	private String celular;
	
	private String pass;
	
	private boolean isValidated;
	
	
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	
	public String getPass() {
		return pass;
	}
	
	
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
	public boolean isValidated() {
		return isValidated;
	}
	
	
	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}
	
	
	@Override
	public String toString() {
		return "Proveedor [correo=" + correo + ", nombre=" + nombre + ", celular=" + celular + "]";
	}
	
	
	
}
