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

import org.hibernate.annotations.CreationTimestamp;

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
	
	@ManyToOne
	@JoinColumn(name="id_usuario", referencedColumnName="id_usuario")
	private Usuario idUsuario;
	
	@Column(name ="telefono")
	private String telefono;
	
	@ManyToOne
	@JoinColumn(name="id_ubicacion", referencedColumnName="id")
	private Ubicacion idUbicacion;
	
	@ManyToOne
	@JoinColumn(name="subcategoria", referencedColumnName="id")
	private SubCategoria subCategoria;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "nombre_empresa")
	private String nombreEmpresa;
	
	@Column(name = "calle")
	private String calle;
	
	@Column(name = "numero_exterior")
	private String numeroExterior;
	
	@Column(name = "isValid")
	private boolean valido;
	
	@Column(name = "likes")
	private int likes;
	
	@CreationTimestamp
	@Column(name = "registro", updatable = false)
	private Timestamp registro;
}
