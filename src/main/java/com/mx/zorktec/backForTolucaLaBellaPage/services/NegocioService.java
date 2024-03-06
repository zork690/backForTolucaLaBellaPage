package com.mx.zorktec.backForTolucaLaBellaPage.services;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CredencialesVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.LoginVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SettingPassProveedorVo;
import com.mx.zorktec.backForTolucaLaBellaPage.exceptions.ProveedorException;

public interface NegocioService {

	public void insertarNegocio(NegocioVo negocio);
	public void setPassProveedor(CredencialesVo credenciales)throws ProveedorException;
	public void setPassProveedor(SettingPassProveedorVo credenciales)throws ProveedorException;
	//public LoginVo validarProveedor(CredencialesVo credenciales)throws NoSuchFieldException
	//, IllegalAccessException;
}
