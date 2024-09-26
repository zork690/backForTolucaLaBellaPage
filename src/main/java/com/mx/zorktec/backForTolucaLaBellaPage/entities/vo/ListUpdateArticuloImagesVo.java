package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListUpdateArticuloImagesVo implements Serializable {

	private static final long serialVersionUID = 1696606948833L;
	
	@NotEmpty(message = "La lista de imagenes no puede estar vacia.")
	private List<@Valid UpdateArticuloImagesVo> imagenes;

}
