package com.mx.zorktec.backForTolucaLaBellaPage.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "negocios")
@Getter
@Setter
@ToString
public class Negocio implements IGenericEntity, Serializable{

	private static final long serialVersionUID = 1367896444L;
	
	@Column(name ="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Id
	@Column(name ="id_negocio")
	private String idNegocio;
	
	@Column(name ="nombre")
	private String nombre;
	
	@Column(name ="telefono")
	private String telefono;
	
	@Column(name = "correo")
	private String email;
	
	@ManyToOne
	@JoinColumn(name="id_ubicacion", referencedColumnName="id")
	private Ubicacion idUbicacion;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "calle")
	private String calle;
	
	@Column(name = "categoria")
	private String categoria;
	
	@Column(name = "nombre_empresa")
	private String nombreEmpresa;
	
	@Column(name = "numero_exterior")
	private String numeroExterior;
}
