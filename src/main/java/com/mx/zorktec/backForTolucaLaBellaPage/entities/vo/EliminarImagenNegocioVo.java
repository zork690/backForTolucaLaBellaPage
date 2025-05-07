package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EliminarImagenNegocioVo {
	
	@NotBlank(message="IdImagen es requerida")
	@Pattern(message="IdNegocio solo números", regexp = "^[0-9]+$")
	private String idImagen;
	
	@NotBlank(message="IdNegocio es requerido")
	@Pattern(message="IdNegocio solo números", regexp = "^[0-9]+$")
	private String idNegocio;

}
