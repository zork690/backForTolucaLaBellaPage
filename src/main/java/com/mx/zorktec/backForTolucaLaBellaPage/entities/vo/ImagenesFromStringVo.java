package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImagenesFromStringVo {

	@NotBlank(message="Nombre de imagen es requerida")
	@Pattern(message="Nombre contiene algun(os) caracter(es) no permitido(s)", regexp = "^[a-zA-Z0-9 ._-]+$")
	private String nombre;
	
	@NotBlank(message="Base64 de imagen es requerida")
	@Pattern(message="Base64 de imagen contiene algun(os) caracter(es) no permitido(s)", regexp = "^(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=)?$")
	private String baseContent;
}
