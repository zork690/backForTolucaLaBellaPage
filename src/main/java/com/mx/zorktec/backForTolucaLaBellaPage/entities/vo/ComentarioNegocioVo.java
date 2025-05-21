package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ComentarioNegocioVo {
	
	@NotBlank(message="Comentario requerido")
	@Size(max=50, message = "Comentario máximo 350 carácteres")
	@Pattern(message="Comentario carácteres no permitidos", regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ$%\"!0-9()?¿=.,#& ]+$")
	private String comentario;
	
	@NotBlank(message="idNegocio requerido")
	@Pattern(message="idNegocio solo números", regexp = "^[0-9]+$")
	private String idNegocio;

}
