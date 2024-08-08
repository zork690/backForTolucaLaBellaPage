package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroVo {

	@NotBlank(message="Usuario requerido")
	@Size(max=50, message = "Usuario máximo 30 carácteres")
	@Pattern(message="Usuario solo letras o números sin espacio", regexp = "^[a-zA-Z0-9]+$")
	private String usuario;
	
	@NotBlank(message="Nombre requerido")
	@Size(max=50, message = "Nombre máximo 50 carácteres")
	@Pattern(message="Nombre solo letras", regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")
	private String nombre;
	
	@NotBlank(message="Apellido requerido")
	@Size(max=50, message = "Apellido máximo 70 carácteres")
	@Pattern(message="Apellido solo letras", regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")
	private String apellido;
	
	@NotBlank(message="Correo requerido")
	@Email(message = "Correo formato inválido", regexp="^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
	private String correo;
	
	@NotBlank(message="Teléfono requerido")
	@Size(min=10, max=10, message = "Teléfono 10 carácteres")
	@Pattern(message="Teléfono solo números", regexp = "^\\d{10}$")
	private String telefono;
	
	@NotBlank(message="Contraseña requerida")
	@Size(min=8, message = "Contraseña mínimo 8 carácteres")
	private String password;
}
