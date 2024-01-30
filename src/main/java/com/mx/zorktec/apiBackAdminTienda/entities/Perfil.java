package com.mx.zorktec.apiBackAdminTienda.entities;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


//@Entity
//@Table(name = "perfiles")
@Getter
@Setter
public class Perfil implements IGenericEntity, Serializable{

	private static final long serialVersionUID = 150596868693985865L;
	
	//@Id
	//@Column(name = "id_perfil")
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//@Column(name = "desc_perfil")
	private String descPerfil;
	
	
}
