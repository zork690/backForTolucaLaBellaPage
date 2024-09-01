package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubCategoriaVo implements Serializable{
	
	private static final long serialVersionUID = 18904567L;
	
	private int id;
	private String nombre;
	private boolean valid;
	private CategoriaSubCategoriaVo categoria;

}
