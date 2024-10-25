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
@Table(name = "noticias")
@Getter
@Setter
@ToString
public class Noticia implements IGenericEntity, Serializable {
	
	private static final long serialVersionUID = 1560696836666L;
	
	@Id
	@Column(name ="id_noticia")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "noticia")
	private String noticia;
	
	@Column(name ="imagen")
	private String imagen;
	
	@Column(name ="valid")
	private boolean valid;

}
