package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubCategoria_Vo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	@NotNull(message= "Id categoria es requerido")
	@Range(min = 1, message ="Id categoría es requerido")
	private Integer categoria;
	
	@NotBlank(message="Subcategoría requerida")
	@Size(max=50, message = "Subcategoría máximo 50 carácteres")
	@Pattern(message="Subcategoría solo letras sin acentos ni ñes", regexp = "^[a-zA-Z ]+$")
	private String subcategoria;

	private boolean valid;

}
