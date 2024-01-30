package com.mx.zorktec.backForTolucaLaBellaPage.entities;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

//@Entity
//@Table(name = "permisos")
@Getter
@Setter
public class Permisos implements IGenericEntity, Serializable{

	private static final long serialVersionUID = 18989985059686721L;
	
	//@Id
	//@Column(name ="id_permiso", precision = 22, scale = 0)
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//@Column(name ="nombre_permiso")
	private String nombre;
	
	//@Column(name ="descripcion_permiso")
	private String descripcion;

}
