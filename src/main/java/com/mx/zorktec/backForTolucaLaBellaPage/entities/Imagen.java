package com.mx.zorktec.backForTolucaLaBellaPage.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "imagenes_negocios")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Imagen implements IGenericEntity, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int numImagen;

	@Column(name="url")
	private String url;

	@ManyToOne
	@JoinColumn(name="id_negocio", referencedColumnName="id_negocio")
	private Negocio idNegocio;

}
