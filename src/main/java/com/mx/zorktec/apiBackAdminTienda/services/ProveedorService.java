package com.mx.zorktec.apiBackAdminTienda.services;

import com.mx.zorktec.apiBackAdminTienda.entities.vo.CredencialesVo;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.LoginVo;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.ProveedorVo;
import com.mx.zorktec.apiBackAdminTienda.entities.vo.SettingPassProveedorVo;
import com.mx.zorktec.apiBackAdminTienda.exceptions.ProveedorException;

public interface ProveedorService {

	public void insertarProveedor(ProveedorVo proveedor);
	public void setPassProveedor(CredencialesVo credenciales)throws ProveedorException;
	public void setPassProveedor(SettingPassProveedorVo credenciales)throws ProveedorException;
	public LoginVo validarProveedor(CredencialesVo credenciales)throws NoSuchFieldException, IllegalAccessException;
}
