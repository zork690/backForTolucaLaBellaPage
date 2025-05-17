package com.mx.zorktec.backForTolucaLaBellaPage.services.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mx.zorktec.backForTolucaLaBellaPage.daos.UsuarioDao;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.Usuario;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.NegocioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.entities.vo.UsuarioVo;
import com.mx.zorktec.backForTolucaLaBellaPage.services.UsuarioService;
import com.mx.zorktec.backForTolucaLaBellaPage.utilities.Utilities;

@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	private static final Logger LOG = LogManager.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private UsuarioDao usuarioDao;
	
	@Override
	public void insertarUsuario(UsuarioVo usuario) {
		Usuario u = new Usuario();
		String randomId = Utilities.generateIdForClient();
		LOG.info("RANDOM STRING FOR NEW USER: "+randomId);
		u.setIdUsuario(randomId);
		u.setNombre(usuario.getNombre());
		u.setEmail(usuario.getEmail());
		u.setTelefono(usuario.getTelefono());		
		this.usuarioDao.saveOrUpdate(u);
	}

	@Override
	public UsuarioVo generateUsuarioVo(NegocioVo negocioVo) {
		UsuarioVo u = new UsuarioVo();
		u.setNombre(negocioVo.getNombre());
		u.setEmail(negocioVo.getCorreo());
		u.setTelefono(negocioVo.getTelefono());
		return u;
	}

	@Override
	public Usuario getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario getByEmail(String email) {
		return this.usuarioDao.findByEmail(email).orElse(null);
	}

	@Override
	public void updateUserValidation(String email) {
		this.usuarioDao.updateValidUser(email);	
	}
	
	
}
