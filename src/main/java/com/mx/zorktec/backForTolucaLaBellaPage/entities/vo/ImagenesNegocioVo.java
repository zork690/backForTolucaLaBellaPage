package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImagenesNegocioVo {
	
	@NotBlank(message="IdNegocio es requerido")
	@Pattern(message="IdNegocio solo números", regexp = "^[0-9]+$")
	private String idNegocio;
	
	@Size(min=1, max=10, message = "Imágenes negocio mínimo 1, máximo 10")
	private List<@Valid ImagenesNegociosVo> imagenes;

}
