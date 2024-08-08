package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroVo {

	@NotBlank(message="Usuario requerido")
	private String usuario;
	
	@NotBlank(message="Nombre requerido")
	private String nombre;
	
	@NotBlank(message="Apellido requerido")
	private String apellido;
	
	@NotBlank(message="Correo requerido")
	@Email(message = "Correo formato inválido", regexp="^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
	private String correo;
	
	@NotBlank(message="Teléfono requerido")
	private String telefono;
	
	@NotBlank(message="Contraseña requerida")
	@Size(min=8, message = "Contraseña mínimo 8 carácteres")
	private String password;
}
