package com.mx.zorktec.backForTolucaLaBellaPage.services;

import java.util.List;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.CredencialesVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegociosInfoVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.SettingPassProveedorVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.TokenPayloadVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.UpdateNegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.exceptions.ProveedorException;

public interface NegocioService {

	public void insertarNegocio(NegocioVo negocio);
	public void setPassProveedor(CredencialesVo credenciales)throws ProveedorException;
	public void setPassProveedor(SettingPassProveedorVo credenciales)throws ProveedorException;
	public List<NegociosInfoVo> getNegocios();
	public List<NegociosInfoVo> getNegociosTodos();
	public List<NegociosInfoVo> getNegociosFavoritos();
	public NegociosInfoVo getNegocioById(String id) throws Exception;
	public void actualizarNegocio(UpdateNegocioVo negocio, TokenPayloadVo tokenInfo) throws NullPointerException;
	public void insertarNegocioUserLoggued(UpdateNegocioVo negocio, TokenPayloadVo tokenInfo);
	public List<NegociosInfoVo> getNegociosBySubCategoria(String subcategoria);
	public List<NegociosInfoVo> getNegociosbyUser(TokenPayloadVo tokenInfo);
}
