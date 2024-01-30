package com.mx.zorktec.backForTolucaLaBellaPage.daos.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.PermisosPerfilesDAO;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Permisos;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.PermisosPerfil;

//@Repository
public class PermisosPerfilesDaoImpl extends GenericDaoImpl<PermisosPerfil> implements PermisosPerfilesDAO{

	/*@Override
	public List<PermisosPerfil> obtienePermisosByPerfil(Long idPerfil) {
		
		
		List<PermisosPerfil> permisosList = new ArrayList<PermisosPerfil>();
		PermisosPerfil p = new PermisosPerfil();
		p.setEstatus(1L);
		Permisos permisos = new Permisos();
		permisos.setNombre("altaImagenes");
		p.setIdPermiso(permisos);
		permisosList.add(p);
		return permisosList;
	}

	@Override
	protected Class<PermisosPerfil> getType() {
		// TODO Auto-generated method stub
		return null;
	} */

}
