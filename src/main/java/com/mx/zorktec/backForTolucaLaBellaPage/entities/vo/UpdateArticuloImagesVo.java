package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateArticuloImagesVo implements Serializable {

	private static final long serialVersionUID = 198765437889211L;
	
	@NotBlank(message="Id de la imagen es requerida")
	@Pattern(message="Id de la imagen solo pueden ser números", regexp = "^[0-9]+$")
	private String id;
	
	@NotBlank(message="valid es campo requerido")
	@Pattern(message="valid solo puede ser true o false", regexp = "^true$|^false$")
	private String valid;

}
