package com.mx.zorktec.backForTolucaLaBellaPage.services;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Usuario;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.UsuarioVo;

public interface UsuarioService {

	public void insertarUsuario(UsuarioVo usuario);
	public UsuarioVo generateUsuarioVo(NegocioVo negocioVo);
	public Usuario getById(String id);
	public Usuario getByEmail(String email);
}
