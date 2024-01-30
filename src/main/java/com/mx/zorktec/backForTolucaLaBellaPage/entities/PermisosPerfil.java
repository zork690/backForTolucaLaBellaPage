package com.mx.zorktec.backForTolucaLaBellaPage.entities;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

//@Entity
//@Table(name = "permisos_perfiles")
@Getter
@Setter
public class PermisosPerfil implements IGenericEntity, Serializable{

	private static final long serialVersionUID = 959638799976L;
	
	//@Id
	//@Column(name ="id_permiso", precision = 22, scale = 0)
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "fk_id_perfil")
	private Perfil idPerfil;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name = "fk_id_var")
	private Permisos idPermiso;
	
	//@Column(name ="estatus")
	private Long estatus;
	
	public boolean getBandera() {
		return this.estatus==1;
	}
}
