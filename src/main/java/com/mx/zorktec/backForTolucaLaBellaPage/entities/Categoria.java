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
@Table(name = "categorias")
@Getter
@Setter
@ToString
public class Categoria implements IGenericEntity, Serializable{
	
	private static final long serialVersionUID = 16789032L;
	
	@Id
	@Column(name ="id_categoria")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name ="categoria")
	private String categoria;
	
	@Column(name ="imagen")
	private String imagen;
	
	@Column(name ="valid")
	private boolean valid;

}
