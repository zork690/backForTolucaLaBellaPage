package com.mx.zorktec.backForTolucaLaBellaPage.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario implements IGenericEntity, Serializable{

	private static final long serialVersionUID = 158584743732847564L;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Id
	@Column(name ="id_usuario")
	private String idUsuario;
	
	@Column(name ="nombre")
	private String nombre;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "password")
	private String pass;
	
	@Column(name = "isValid")
	private boolean valido;
	
}
