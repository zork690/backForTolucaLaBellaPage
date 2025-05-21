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
@Table(name = "negocio_comentarios")
@Getter
@Setter
@ToString
public class NegocioComentario implements IGenericEntity, Serializable {
	
	private static final long serialVersionUID = 56678964449L;
	
	@Id
	@Column(name ="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="id_negocio", referencedColumnName="id_negocio")
	private Negocio idNegocio;
	
	@ManyToOne
	@JoinColumn(name="id_usuario", referencedColumnName="id_usuario")
	private Usuario idUsuario;
	
	@Column(name ="comentario")
	private String comentario;

}
