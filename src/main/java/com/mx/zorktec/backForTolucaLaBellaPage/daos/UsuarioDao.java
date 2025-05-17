package com.mx.zorktec.backForTolucaLaBellaPage.daos;

import java.util.List;
import java.util.Optional;

import com.mx.zorktec.backForTolucaLaBellaPage.entities.Usuario;

public interface UsuarioDao extends IGenericDao<Usuario>{

	public Optional<List<Usuario>> findAll();
	public Optional<List<Usuario>> findOnlyValids();
	public Optional<Usuario> findByEmail(String email);
	public void updateValidUser(String email);
}
