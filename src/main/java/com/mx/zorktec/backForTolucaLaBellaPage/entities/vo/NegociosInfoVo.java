package com.mx.zorktec.backForTolucaLaBellaPage.entities.vo;

import java.io.Serializable;
import java.util.List;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Ubicacion;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NegociosInfoVo implements Serializable{

	private static final long serialVersionUID = 15069787L;
	
	private int id;
	private String idNegocio;
	private String nombre;
	private String telefono;
	private String email;
	private Ubicacion ubicacion;
	private String descripcion;
	private String calle;
	private SubCategoriaVo subcategoria;
	private String nombrEmpresa;
	private String numeroExterior;
	private boolean valid;
	private List<ImagenNegocioVo> imagenes;

}
