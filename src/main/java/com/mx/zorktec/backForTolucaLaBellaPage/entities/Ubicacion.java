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
@Table(name = "ubicaciones")
@Getter
@Setter
public class Ubicacion implements IGenericEntity, Serializable {

	private static final long serialVersionUID = 1567896444L;
	
	@Id
	@Column(name ="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name ="colonia")
	private String colonia;
	
	@Column(name ="municipio")
	private String municipio;
	
	@Column(name ="codigo_postal")
	private int codigoPostal;

}
