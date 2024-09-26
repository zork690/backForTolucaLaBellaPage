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
import lombok.ToString;

@Entity
@Table(name = "articulos")
@Getter
@Setter
@ToString
public class Articulo implements IGenericEntity, Serializable{
	
	private static final long serialVersionUID = 1560696836789L;
	
	@Id
	@Column(name ="id_articulo")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "isValid")
	private boolean valido;

}
