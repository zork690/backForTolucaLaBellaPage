package com.mx.zorktec.backForTolucaLaBellaPage.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Entity
//@Table(name = "imagenes")
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
public class Imagen implements Serializable {

	private static final long serialVersionUID = 1L;

	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	@Getter
	@Setter
	private int numImagen;

	@Getter
	@Setter
	private String imagen;

	@Getter
	@Setter
	private String descripcion;

	@Getter
	@Setter
	private double precio;

	@Getter
	@Setter
	private boolean estatus;

	@Getter
	@Setter
	private Timestamp registro; */

}
