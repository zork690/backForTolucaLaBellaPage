package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticiaVo implements Serializable {

	private static final long serialVersionUID = 2345678987L;

	private int id;
	
	@NotBlank(message="Nombre noticia requerido")
	@Size(max=50, message = "Noticia máximo 50 carácteres")
	@Pattern(message="Nombre solo letras sin acentos ni ñes", regexp = "^[a-zA-Z ]+$")
	private String noticia;
	
	@NotBlank(message="Imagen requerida")
	@Pattern(message="Imagen formato inválido", regexp = "^(?:[A-Za-z0-9+\\/]{4})*(?:[A-Za-z0-9+\\/]{2}==|[A-Za-z0-9+\\/]{3}=|[A-Za-z0-9+\\/]{4})$")
	private String imagen;

	private boolean valid;
}
