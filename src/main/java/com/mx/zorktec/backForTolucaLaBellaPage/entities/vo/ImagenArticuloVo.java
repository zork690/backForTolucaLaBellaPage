package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ImagenArticuloVo implements Serializable{

	private static final long serialVersionUID = 13456890354L;
	
	private int id;
	private String nombre;
	private int idArticulo;
	private boolean valid;
}
