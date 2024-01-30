package com.mx.zorktec.backForTolucaLaBellaPage.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


//@Entity
//@Table(name = "usuarios")
//@Getter
//@Setter
public class Usuario implements IGenericEntity, Serializable{

	private static final long serialVersionUID = 158584743732847564L;
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "id_proveedor")
	private Long id;
	
	private boolean isValidated;
	private String correo;
	private String nombre;
	private String pass;
	private String telefono;
	private Perfil idPerfil;
	
}
