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
public class ArticuloReceivedVo implements Serializable{

	private static final long serialVersionUID = 1346901L;

	private int id;
	
	@NotBlank(message="Nombre de artículo es requerido")
	@Size(max=128, message = "Nombre artículo máximo 128 carácteres")
	@Pattern(message="Nombre artículo solo letras o números", regexp = "^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ ]+$")
	private String nombre;
	
	@NotBlank(message="Descripción del artículo es requerido")
	@Size(max=2024, message = "Descripción del artículo máximo 2024 carácteres")
	@Pattern(message="Descripción del artículo contiene carácteres no permitidos", regexp = "^[a-zA-Z0-9ñÑáéíóúÁÉÍÓÚ.,\"\\-$()¿?!¡%&\\n\\r“”:; ]+$")
	private String descripcion;
	
	@NotBlank(message="Id de la noticia es requerido")
	@Pattern(message="Id noticia solo números", regexp = "^[0-9]+$")
	private String idNoticia;
	
	private boolean valid;

}
