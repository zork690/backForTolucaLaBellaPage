package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticuloVo implements Serializable{
	
	private static final long serialVersionUID = 16079838383L;
	
	private int id;
	private String nombre;
	private String descripcion;
	private List<ImagenArticuloVo> imagenes;
	private boolean valid;

}
