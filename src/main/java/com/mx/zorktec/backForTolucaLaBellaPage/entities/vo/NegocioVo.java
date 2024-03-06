package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NegocioVo {
	
	@NotBlank(message="Nombre requerido")
	@Size(max=50, message = "Nombre máximo 50 carácteres")
	@Pattern(message="Nombre solo letras", regexp = "^[A-Za-z ]+$")
	private String nombre;
	
	@NotBlank(message="Teléfono requerido")
	@Size(min=10, max=10, message = "Teléfono 10 carácteres")
	@Pattern(message="Teléfono solo números", regexp = "^\\d{10}$")
	private String telefono;
	
	@NotBlank (message="Correo requerido")
	@Email(message = "Correo formato inválido", regexp="^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
	private String correo;
	
	@NotBlank(message="Nombre de la empresa es requerido")
	@Size(max=50, message = "Nombre de la empresa máximo 50 carácteres")
	@Pattern(message="Nombre de la empresa solo letras o números", regexp = "^[a-zA-Z0-9]+$")
	private String nombreEmpresa;
	
	@NotBlank(message="Calle es requerida")
	@Size(max=50, message = "Calle máximo 50 carácteres")
	@Pattern(message="Calle solo letras o números", regexp = "^[a-zA-Z0-9 ]+$")
	private String calle;
	
	@NotBlank(message="Número exterior es requerido")
	@Size(max=10, message = "Número de calle máximo 10 números")
	@Pattern(message="Número exterior solo números", regexp = "^[0-9]+$")
	private String numeroExterior;
	
	@NotBlank(message="El id de la ubicación es requerido")
	@Pattern(message="Id ubicación solo números entre 1 a 654", regexp = "([1-9]|[1-9][0-9]|[1-5][0-9][0-9]|6[0-4][0-9]|65[0-4])")
	private String idUbicacion;
	
	@NotBlank(message="Descripción comercial es requerida")
	@Size(max=150, message = "Descripción comercial máximo 150 carácteres")
	@Pattern(message="Descripción comercial solo letras o números", regexp = "^[a-zA-Z0-9 ]+$")
	private String descripcionComercial;
	
	@NotBlank(message="Categoría es requerida")
	@Size(max=50, message = "Categoría máximo 50 carácteres")
	@Pattern(message="Categoría solo letras", regexp = "^[A-Za-z ]+$")
	private String categoria;
}
