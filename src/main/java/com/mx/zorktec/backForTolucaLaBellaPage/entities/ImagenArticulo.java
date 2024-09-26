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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "imagenes_articulos")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImagenArticulo implements IGenericEntity, Serializable{

	private static final long serialVersionUID = 1456789035L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="nombre")
	private String nombre;

	@ManyToOne
	@JoinColumn(name="id_articulo", referencedColumnName="id_articulo")
	private Articulo articulo;
	
	@Column(name = "isValid")
	private boolean valid;

}
