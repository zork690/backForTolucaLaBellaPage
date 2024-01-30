package com.mx.zorktec.apiBackAdminTienda.daos;

import java.util.List;

import com.mx.zorktec.apiBackAdminTienda.entities.PermisosPerfil;

public interface PermisosPerfilesDAO extends IGenericDao<PermisosPerfil>{
	
	List<PermisosPerfil> obtienePermisosByPerfil (Long idPerfil);

}
