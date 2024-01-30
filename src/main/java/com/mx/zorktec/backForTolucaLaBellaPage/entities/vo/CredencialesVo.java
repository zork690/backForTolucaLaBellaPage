package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.ToString;

@ToString
public class CredencialesVo {

	@NotBlank(message="Correo requerido")
	@Email(message = "Correo formato inválido", regexp="^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
	private String email;
	
	@NotBlank(message="Contraseña requerida")
	private String pass;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
	
	
}
