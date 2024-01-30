package com.mx.zorktec.apiBackAdminTienda.daos;


import com.mx.zorktec.apiBackAdminTienda.entities.Proveedor;
import com.mx.zorktec.apiBackAdminTienda.entities.Usuario;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.CredencialesVo;

public interface ProveedorDao extends IGenericDao<Proveedor>{

	public Proveedor findByEmail(CredencialesVo credenciales);
	public Usuario validarProveedor(CredencialesVo usuario);
}
