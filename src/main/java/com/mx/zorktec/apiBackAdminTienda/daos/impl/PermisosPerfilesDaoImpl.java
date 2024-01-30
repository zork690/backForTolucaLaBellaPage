package com.mx.zorktec.apiBackAdminTienda.daos.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mx.zorktec.apiBackAdminTienda.daos.PermisosPerfilesDAO;
import com.mx.zorktec.apiBackAdminTienda.entities.Permisos;
import com.mx.zorktec.apiBackAdminTienda.entities.PermisosPerfil;

@Repository
public class PermisosPerfilesDaoImpl extends GenericDaoImpl<PermisosPerfil> implements PermisosPerfilesDAO{

	@Override
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
	}

}
