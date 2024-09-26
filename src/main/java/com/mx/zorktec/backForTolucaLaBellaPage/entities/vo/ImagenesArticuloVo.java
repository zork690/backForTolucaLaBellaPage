package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImagenesArticuloVo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotBlank(message="Id de artículo es requerido")
	@Pattern(message="Id del artículo solo números", regexp = "^[0-9]+$")
	private String idArticulo;
	
	@NotEmpty(message="Imágenes son requeridas")
	private List<@Valid ImagenesFromStringVo> imagenes;

}
