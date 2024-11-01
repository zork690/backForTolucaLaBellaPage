package com.mx.zorktec.backForTolucaLaBellaPage.services;

import java.util.List;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CredencialesVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.ImagenNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.LoginVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegociosInfoVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SettingPassProveedorVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.UpdateNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.exceptions.ProveedorException;

public interface NegocioService {

	public void insertarNegocio(NegocioVo negocio);
	public void setPassProveedor(CredencialesVo credenciales)throws ProveedorException;
	public void setPassProveedor(SettingPassProveedorVo credenciales)throws ProveedorException;
	public List<NegociosInfoVo> getNegocios();
	public List<NegociosInfoVo> getNegociosTodos();
	public NegociosInfoVo getNegocioById(String id) throws Exception;
	public void actualizarNegocio(UpdateNegocioVo negocio) throws NullPointerException;
	//public LoginVo validarProveedor(CredencialesVo credenciales)throws NoSuchFieldException
	//, IllegalAccessException;
	public List<NegociosInfoVo> getNegociosBySubCategoria(String subcategoria);
}
