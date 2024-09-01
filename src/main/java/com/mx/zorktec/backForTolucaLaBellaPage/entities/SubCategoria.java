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
@Table(name = "subcategorias")
@Getter
@Setter
@ToString
public class SubCategoria implements IGenericEntity, Serializable{
	
	private static final long serialVersionUID = 1358765890L;
	
	@Id
	@Column(name ="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="categoria", referencedColumnName="id_categoria")
	private Categoria categoria;
	
	private String subcategoria;
	private boolean valid;

}
