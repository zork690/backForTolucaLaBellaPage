package com.mx.zorktec.apiBackAdminTienda.entities.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ProveedorVo {
	
	
	@NotBlank(message="Correo requerido")
	@Email(message = "Correo formato inválido", regexp="^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
	private String correo;
	
	@NotBlank(message="Nombre requerido")
	@Size(max=50, message = "Nombre máximo 50 carácteres")
	//@Max(50)
	private String nombre;
	
	@NotBlank(message="Teléfono requerido")
	@Size(min=10, max=10, message = "Teléfono 10 carácteres")
	@Pattern(message="Teléfono solo números", regexp = "^\\d{10}$")
	private String telefono;
	
	
	
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
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
}
